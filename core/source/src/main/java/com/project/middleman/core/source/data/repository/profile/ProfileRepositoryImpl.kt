package com.project.middleman.core.source.data.repository.profile

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.UserDTO
import com.project.middleman.core.source.data.model.UserProfile
import com.project.middleman.core.source.domain.profile.repository.AddUserProfileResponse
import com.project.middleman.core.source.domain.profile.repository.CheckDisplayNameResponse
import com.project.middleman.core.source.domain.profile.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.profile.repository.ProfileRepository
import com.project.middleman.core.source.domain.profile.repository.SignOutResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ProfileRepository {

    override suspend fun signOut(): SignOutResponse {
        return try {
            auth.signOut()
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getUserProfile(userId: String): GetUserProfileResponse {
        return try {
            val user = auth.currentUser?.let { firebaseUser ->
                val snapshot = db.collection("users").document(userId).get().await()
                if (snapshot.exists()) {
                    Log.d("getUserProfile", "${snapshot.data}")
                    snapshot.toObject(UserDTO::class.java)
                } else {
                    null // User document not found
                }
            }

            if (user != null) {
                RequestState.Success(user)
            } else {
                // Handle the scenario where the user document does not exist
                Log.d("ProfileRepository", "getUserObject: Error")
                RequestState.Error(Exception("User not found"))
            }
        } catch (e: Exception) {
            Log.d("ProfileRepository", "getUserObject: Error")
            RequestState.Error(e)
        }
    }


    override suspend fun addUserProfile(user: UserProfile): AddUserProfileResponse {
        val uid = user.uid ?: return RequestState.Error(Exception("Missing UID"))
        val displayName = user.displayName ?: return RequestState.Error(Exception("Missing displayName"))

        return try {
            // References
            val usernameRef = db.collection("displayName").document(displayName)
            val userRef = db.collection("users").document(uid)

            // Transaction: claim username & save user profile
            val success = db.runTransaction<Boolean> { txn ->
                val snapshot = txn.get(usernameRef)

                if (snapshot.exists()) {
                    false // username taken
                } else {
                    // Claim username
                    txn.set(usernameRef, mapOf("uid" to uid))
                    // Save full user profile
                    txn.set(userRef, user)
                    true
                }
            }.await()

            if (success) RequestState.Success(true)
            else RequestState.Error(Exception("Display name already taken"))

        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }


}