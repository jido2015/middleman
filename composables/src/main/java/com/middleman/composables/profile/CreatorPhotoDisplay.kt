package com.middleman.composables.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.project.middleman.designsystem.themes.MiddlemanTheme
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent

@Composable
fun CreatorPhotoDisplay(
    modifier: Modifier = Modifier,
    creatorPhoto: String?, creatorName: String?) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (photo, name, profileType) = createRefs()

        ProfileImage(
            modifier = Modifier
                .constrainAs(photo) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start)
                }
                .border(
                    width = 2.dp,
                    color = colorAccent,
                    shape = RoundedCornerShape(30) // circle, adjust as needed
                )
                .padding(4.dp), // actual padding inside border (white space)
            imageUrl = creatorPhoto,
        )


        Text(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(photo.end, margin = 8.dp)
                    end.linkTo(parent.end) // ensure it doesn't overflow
                    width = Dimension.fillToConstraints // wrap and use available space
                },
            text = "@$creatorName",
            style = Typography.titleSmall.copy(fontSize = 12.sp),
            maxLines = 1, // optional: limit to 2 lines
            overflow = TextOverflow.Ellipsis // optional: show ... if too long
        )


        Text(
            modifier = Modifier.constrainAs(profileType) {
                top.linkTo(name.bottom, margin = 2.dp)
                start.linkTo(photo.end, margin = 8.dp)
            },
            text = "Creator", color = colorAccent,
            style = Typography.titleSmall.copy(fontSize = 12.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreatorPhotoDisplayPreview() {
    MiddlemanTheme {   // replace with your theme composable
        CreatorPhotoDisplay(
            creatorPhoto = "",
            creatorName = "",
        )
    }
}
