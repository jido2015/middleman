package com.project.middleman.core.source.data.model


import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

data class UserProfile(
    var uid: String?,
    var displayName: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?,
    var dob: String?,
    var phoneNumber: String?,
    var photoUrl: String,
    var createdAt: FieldValue? = null
)


fun UserProfile.toDTO(): UserDTO {
    val timestamp = when (createdAt) {
        else -> Timestamp.now()
    }
    return UserDTO(
        uid = uid ?: "",
        displayName = displayName ?: "",
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        dob = dob ?: "",
        phoneNumber = phoneNumber ?: "",
        photoUrl = photoUrl,
        createdAt = timestamp
    )
}



@IgnoreExtraProperties
data class UserDTO(
    var uid: String?="",
    var displayName: String?="",
    var email: String?="",
    var firstName: String? ="",
    var lastName: String? ="",
    var dob: String? ="",
    var phoneNumber: String?="",
    var photoUrl: String? ="",
    var createdAt: Timestamp? = Timestamp.now(),
){
    constructor() : this("","", "", "", "",null)
}