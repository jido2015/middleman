package com.project.middleman.core.source.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity(tableName = "userData")
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "uid") val uid: String?,
    @ColumnInfo(name = "displayName") val displayName: String?,
    @ColumnInfo(name = "firstName") val firstName: String?,
    @ColumnInfo(name = "lastName") val lastName: String?,
    @ColumnInfo(name = "dob") val dob: String?,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String?,
    @ColumnInfo(name = "photoUrl") val photoUrl: String?,
    @ColumnInfo(name = "createdAt") val createdAt: Timestamp?,
    @ColumnInfo(name = "email") val email: String?
)