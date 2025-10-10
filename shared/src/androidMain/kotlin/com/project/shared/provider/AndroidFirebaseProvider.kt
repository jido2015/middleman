package com.project.shared.provider

import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import dev.gitlive.firebase.storage.FirebaseStorage
import dev.gitlive.firebase.storage.storage

// Provide via Koin
class AndroidFirebaseProvider(context: Context): FirebaseProvider {

    init {
        Firebase.initialize(context)
    }

    override val firebaseAuth: FirebaseAuth  get() = Firebase.auth
    override val firebaseFirestore: FirebaseFirestore get() = Firebase.firestore
    override val firebaseStorage: FirebaseStorage get() = Firebase.storage

}