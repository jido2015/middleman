package com.project.middleman.core.common.upload.worker

import android.content.Context
import android.net.Uri
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.gson.Gson
import com.project.middleman.core.source.data.model.Challenge
import java.util.UUID


/**
 * Step 2: Enqueue Work
 * When user picks a file, instead of uploading immediately, enqueue the worker:
 */

fun enqueueMultipleEvidenceUploads(
    ctx: Context, challengeId: String, userId: String,
    uris: List<Uri>, disputeNote: String,
    challengeStatus: String
) {

    val wm = WorkManager.getInstance(ctx)

    uris.forEach { uri ->
        // 1️⃣ Generate a unique ID for this work
        val workId = UUID.randomUUID().toString()

        // 2️⃣ Build the WorkRequest with input data including workId
        val work = OneTimeWorkRequestBuilder<EvidenceUploadWorker>()
            .setInputData(
                workDataOf(
                    "challengeId" to challengeId,
                    "userId" to userId,
                    "fileUri" to uri.toString(),
                    "workId" to workId,
                    "note" to disputeNote,
                    "challengeStatus" to challengeStatus
                )
            )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // only upload when online
                    .build()
            )
            .build()

        // 3️⃣ Enqueue the work
        wm.enqueue(work)
    }

}