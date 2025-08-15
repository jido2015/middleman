package com.project.middleman.feature.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.project.middleman.core.source.data.local.UserLocalDataSource
import com.project.middleman.core.source.data.mapper.toEntity
import com.project.middleman.core.source.data.model.UserDTO
import com.project.middleman.core.source.data.model.UserProfile
import com.project.middleman.core.source.data.model.toDTO
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.authentication.repository.AddUserProfileResponse
import com.project.middleman.core.source.domain.authentication.usecase.AddUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val addUserProfileUseCase: AddUserProfileUseCase,
    private val local: UserLocalDataSource
    ) : ViewModel() {
    var loadingState = mutableStateOf(false)

    private val _addUserProfile = MutableStateFlow<AddUserProfileResponse>(RequestState.Loading)
    val addUserProfile: StateFlow<RequestState<Boolean>> = _addUserProfile
    var fullName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var dob by mutableStateOf("")
    var displayName by mutableStateOf("")

    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }

    fun addUserProfile() {
        val userProfile = UserProfile(
            firstName = fullName.split(" ").firstOrNull(),
            lastName = fullName.split(" ").lastOrNull(),
            phoneNumber = phoneNumber,
            displayName = displayName,
            dob = dob,
            uid = firebaseAuth.currentUser?.uid,
            photoUrl = firebaseAuth.currentUser?.photoUrl.toString(),
            email = firebaseAuth.currentUser?.email,
            createdAt = FieldValue.serverTimestamp()
        )
        viewModelScope.launch {
            _addUserProfile.value = RequestState.Loading
            val result = addUserProfileUseCase.invoke(userProfile)
            _addUserProfile.value = result
            syncUserFromFirebase(userProfile.toDTO())
        }
    }

    fun syncUserFromFirebase(
        remote: UserDTO,
    ) = viewModelScope.launch {
        try {
            local.upsert(remote.toEntity()) // Suspends until DB write is done
            Log.d("syncUser", "User saved successfully!") // ✅ Only runs if no error
        } catch (e: Exception) {
            Log.e("syncUser", "Failed to save user", e)
        }
    }

}