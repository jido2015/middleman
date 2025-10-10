package com.project.shared.provider

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.storage.FirebaseStorage

interface FirebaseProvider {
    val firebaseAuth: FirebaseAuth
    val firebaseFirestore: FirebaseFirestore
    val firebaseStorage: FirebaseStorage
}