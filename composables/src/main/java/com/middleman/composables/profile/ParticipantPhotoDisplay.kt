package com.middleman.composables.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent

@Composable
fun ParticipantPhotoDisplay(
    modifier: Modifier = Modifier,
    creatorPhoto: String?, participantName: String?) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (photo, name, profileType) = createRefs()

        ProfileImage(
            modifier = Modifier
                .constrainAs(photo) {
                    top.linkTo(parent.top, margin = 4.dp)
                    end.linkTo(parent.end) // photo on the right
                }
                .padding(4.dp),
            imageUrl = creatorPhoto,
        )

        Text(
            modifier = Modifier.fillMaxWidth()
                .constrainAs(name) {
                    top.linkTo(parent.top, margin = 10.dp)
                    end.linkTo(photo.start, margin = 8.dp) // left of photo
                    start.linkTo(parent.start) // make it stop at start of parent
                    width = Dimension.fillToConstraints // wrap using available width
                },
            text = if (participantName.isNullOrEmpty()) "No yet" else "@$participantName",
            style = Typography.titleSmall.copy(fontSize = 12.sp, textAlign = TextAlign.End),
            maxLines = 1, // limit lines if needed
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.constrainAs(profileType) {
                top.linkTo(name.bottom, margin = 2.dp)
                end.linkTo(photo.start, margin = 8.dp) // left of photo
            },
            text = "Counter",
            color = colorAccent,
            style = Typography.titleSmall.copy(fontSize = 12.sp,  textAlign = TextAlign.End)
        )
    }
}
