package com.project.middleman.core.common.upload.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import java.util.UUID

class CancelUploadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val workIdStr = intent.getStringExtra("workId") ?: return
        val workId = UUID.fromString(workIdStr)
        val notificationId = intent.getIntExtra("notificationId", workId.hashCode())

        // Cancel the work
        WorkManager.getInstance(context).cancelWorkById(workId)

        // Cancel the notification immediately
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(notificationId)
    }
}
