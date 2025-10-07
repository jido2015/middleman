package com.project.middleman

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()

        // Clear Firestore cache once after reinstall / for testing
        FirebaseFirestore.getInstance().clearPersistence()
            .addOnSuccessListener {
                Log.d("App", "Firestore persistence cleared")
            }
            .addOnFailureListener {
                Log.e("App", "Failed to clear Firestore cache: ${it.message}")
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "evidence_uploads",
                "Evidence Uploads",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

    }
}