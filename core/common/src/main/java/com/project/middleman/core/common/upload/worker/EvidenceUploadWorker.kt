package com.project.middleman.core.common.upload.worker

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

/**🔹 Step 1: Define the Worker
 * Create A CoroutineWorker that:
 * Uploads the file to Firebase Storage.
 * Saves metadata to Firestore.
 * Shows progress + success/failure notification in the system tray.
 */
class EvidenceUploadWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {


    override suspend fun doWork(): Result {
        val challengeId = inputData.getString("challengeId") ?: return Result.failure()
        val userId = inputData.getString("userId") ?: return Result.failure()
        val fileUri = inputData.getString("fileUri")?.toUri() ?: return Result.failure()
        val workId = inputData.getString("workId") ?: UUID.randomUUID().toString()
        val evidenceNote = inputData.getString("note") ?: ""
        val challengeStatus = inputData.getString("challengeStatus") ?: return Result.failure()

        val workIdUuid = UUID.fromString(workId)
        val notificationId = System.currentTimeMillis().toInt()
        val channelId = "evidence_uploads"

        return try {
            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("disputes/$challengeId/evidence/$userId/${fileUri.lastPathSegment}")

            // Flow for progress updates
            uploadFileWithProgress(storageRef, fileUri)
                .collect { progress ->
                    // Update WorkManager progress and notification
                    setProgress(workDataOf("progress" to progress))
                    showProgressNotification(
                        notificationId,
                        channelId,
                        challengeId,
                        progress,
                        workIdUuid
                    )
                    showSummaryNotification(channelId)
                }

            // Get download URL after upload
            val downloadUrl = storageRef.downloadUrl.await()

            // Save evidence document
            saveEvidenceToFirestore(
                challengeId, userId, downloadUrl.toString(),
                fileUri, applicationContext, evidenceNote
            )

            // Update challenge status in transaction
            val db = FirebaseFirestore.getInstance()
            val challengeRef = db.collection("challenges").document(challengeId)
            db.runTransaction { transaction ->
                transaction.update(challengeRef, "status", challengeStatus)
                null
            }.await()

            // Final notifications
            showCompletedNotification(notificationId, channelId, challengeId, true)
            showSummaryNotification(channelId)

            // Return download URL as output
            Result.success(
                workDataOf("downloadUrl" to downloadUrl.toString())
            )

        } catch (e: Exception) {
            showCompletedNotification(notificationId, channelId, challengeId, false)
            showSummaryNotification(channelId)
            Result.failure()
        }
    }

    /** Upload helper with progress Flow */
    private fun uploadFileWithProgress(
        storageRef: StorageReference,
        fileUri: Uri
    ): Flow<Int> = callbackFlow {
        val uploadTask = storageRef.putFile(fileUri)

        // Listen for progress updates
        val listener = OnProgressListener<UploadTask.TaskSnapshot> { snapshot ->
            val progress = (100.0 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt()
            trySend(progress).isSuccess // sends progress into the Flow stream
        }

        // Attach Firebase listeners
        uploadTask.addOnProgressListener(listener)
            .addOnSuccessListener { close() } // signals that the flow is complete
            .addOnFailureListener { close() } // signals flow completion on error

        // Keeps flow open until Firebase upload finishes
        awaitClose { uploadTask.removeOnProgressListener(listener) }
    }

}