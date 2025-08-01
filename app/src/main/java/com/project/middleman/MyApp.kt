package com.project.middleman

import android.app.Application
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
    }
}