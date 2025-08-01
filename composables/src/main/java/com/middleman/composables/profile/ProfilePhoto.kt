package com.middleman.composables.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = modifier
            .size(35.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center),
                strokeWidth = 2.dp
            )
        },
        failure = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default avatar",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
    )
}
