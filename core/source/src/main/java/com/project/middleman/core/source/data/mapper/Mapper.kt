package com.project.middleman.core.source.data.mapper

import com.project.middleman.core.source.data.local.entity.UserEntity
import com.project.middleman.core.source.data.model.UserDTO
fun UserDTO.toEntity() = UserEntity(
    uid = uid,
    displayName = displayName,
    firstName = firstName,
    lastName = lastName,
    dob = dob,
    phoneNumber = phoneNumber,
    photoUrl = photoUrl,
    createdAt = createdAt,
    email = email
)
