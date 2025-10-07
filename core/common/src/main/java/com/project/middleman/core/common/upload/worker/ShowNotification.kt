package com.project.middleman.core.common.upload.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.common.Constants.GROUP_KEY_UPLOADS
import com.project.middleman.core.common.R
import com.project.middleman.core.common.upload.broadcast.CancelUploadReceiver
import java.util.UUID
import androidx.core.graphics.toColorInt
import kotlinx.coroutines.tasks.await

fun EvidenceUploadWorker.showProgressNotification(id: Int,
                                                  channelId: String,
                                                  challengeId: String,
                                                  progress: Int,
                                                  workId: UUID
) {
    val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Intent to cancel work + dismiss notification
    val cancelIntent = Intent(applicationContext, CancelUploadReceiver::class.java).apply {
        putExtra("workId", workId.toString())
        putExtra("notificationId", id)
    }

    val cancelPendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        id, // unique per notification
        cancelIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )


    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("Dispute #$challengeId")
        .setContentText("Uploading evidence… $progress%")
        .setSmallIcon(R.drawable.ico)
        .setColor("#6334D8".toColorInt())
        .setProgress(100, progress, false)
        .setOngoing(true)
        .setGroup(GROUP_KEY_UPLOADS) // 👈 group them
        .addAction(android.R.drawable.ic_delete, "Cancel", cancelPendingIntent)

    manager.notify(id, builder.build())
}


fun EvidenceUploadWorker.showCompletedNotification(id: Int, channelId: String, challengeId: String, success: Boolean) {
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("Dispute #$challengeId")
        .setSmallIcon(R.drawable.ico)
        .setColor("#6334D8".toColorInt())
        .setContentText(
            if (success) "Evidence uploaded successfully ✅"
            else "Upload failed ❌"
        )
        .setSmallIcon(
            if (success) R.drawable.ico
            else android.R.drawable.stat_notify_error
        )
        .setProgress(0, 0, false) // clear progress bar
        .setOngoing(false)
        .setGroup(GROUP_KEY_UPLOADS) // 👈 group them

    val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.notify(id, builder.build())
}

//Add a Group Summary Notification
fun EvidenceUploadWorker.showSummaryNotification(channelId: String) {
    val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val summary = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("Dispute Evidence Uploads")
        .setContentText("Multiple uploads in progress")
        .setSmallIcon(R.drawable.ico)
        .setColor("#6334D8".toColorInt())
        .setStyle(NotificationCompat.InboxStyle()) // expandable list
        .setGroup(GROUP_KEY_UPLOADS)
        .setGroupSummary(true) // 👈 important
        .build()

    manager.notify(0, summary) // fixed ID for summary
}

/** Save evidence document to Firestore */
suspend fun saveEvidenceToFirestore(
    challengeId: String,
    userId: String,
    downloadUrl: String,
    fileUri: Uri,
    context: Context,
    note: String
) {
    val db = FirebaseFirestore.getInstance()
    val data = mapOf(
        "userId" to userId,
        "fileUrl" to downloadUrl,
        "fileType" to getFileType(fileUri, context),
        "note" to note,
        "uploadedAt" to FieldValue.serverTimestamp()
    )

    db.collection("disputes")
        .document(challengeId)
        .collection("evidence")
        .add(data)
        .await()
}

