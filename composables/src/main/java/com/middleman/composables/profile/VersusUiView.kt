package com.middleman.composables.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.middleman.composables.R
import com.project.middleman.designsystem.themes.MiddlemanTheme

@Composable
fun VSProfileUiView(
    creatorName: String? = "",
    participantName: String? = "",
    creatorPhoto: String? = "",
    participantPhoto: String? = ""
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {


        CreatorPhotoDisplay( modifier = Modifier.weight(1f), creatorPhoto, creatorName)

        Icon(
            modifier = Modifier.size(15.dp).weight(0.5f),
            painter = painterResource(id = R.drawable.vs),
            contentDescription = null,
            tint = Color.Unspecified // important if you don’t want Compose to recolor your drawable
        )

        ParticipantPhotoDisplay(modifier = Modifier.weight(1f),participantPhoto, participantName)
    }
}



@Preview(showBackground = true)
@Composable
fun VSProfileUiViewPreview() {
    MiddlemanTheme {   // replace with your theme composable
        VSProfileUiView(
            creatorPhoto = "",
            participantPhoto = "",
        )
    }
}

