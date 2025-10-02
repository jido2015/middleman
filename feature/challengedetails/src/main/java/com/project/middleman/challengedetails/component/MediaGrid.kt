package com.project.middleman.challengedetails.component

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MediaGrid(uris: List<Uri>) {
    val context = LocalContext.current

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(uris) { uri ->
            val isVideo = isVideoUri(context, uri)

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                GlideImage(
                    imageModel = { uri },
                    modifier = Modifier.matchParentSize(),
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                    requestOptions = {
                        if (isVideo) {
                            RequestOptions().frame(1_000_000) // 1s thumbnail for video
                        } else {
                            RequestOptions() // default for images
                        }
                    },
                    failure = {
                        Icon(
                            imageVector = Icons.Default.BrokenImage,
                            contentDescription = "Error loading",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center)
                        )
                    }
                )

                if (isVideo) {
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = "Play video",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                            .padding(4.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

fun isVideoUri(context: Context, uri: Uri): Boolean {
    val type = context.contentResolver.getType(uri)
    return type?.startsWith("video") == true
}

