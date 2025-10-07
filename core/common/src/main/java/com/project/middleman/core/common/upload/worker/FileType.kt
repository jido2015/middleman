package com.project.middleman.core.common.upload.worker

import android.content.Context
import android.net.Uri
import kotlin.text.startsWith


fun getFileType(uri: Uri, context: Context): String{
    val type = context.contentResolver.getType(uri)
    return when {
        type?.startsWith("video") == true -> "video"
        type?.startsWith("image") == true -> "image"
        else -> "unknown"
    }
}