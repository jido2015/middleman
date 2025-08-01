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
    var phoneNumber: String?,
    var photoUrl: String,
    var createdAt: FieldValue? = null
){
    constructor() : this("","", "", "", "", "", "", null)

}

@IgnoreExtraProperties
data class UserDTO(
    var uid: String?="",
    var displayName: String?="",
    var email: String?="",
    var firstName: String? ="",
    var lastName: String? ="",
    var phoneNumber: String?="",
    var photoUrl: String? ="",
    var createdAt: Timestamp? = Timestamp.now(),
){
    constructor() : this("","", "", "", null)
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "displayName" to displayName,
            "email" to email,
            "photoUrl" to photoUrl,
            "createdAt" to createdAt,
            "firstName" to firstName,
            "lastName" to lastName,
            "phoneNumber" to phoneNumber
        )
    }
}