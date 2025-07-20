package com.middleman.composables.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.middleman.composables.R
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.surfaceBrandLight
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainToolBar(
    showTopBar: Boolean,
    toolBarTitle: String,
    profilePhoto: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit
) {
    if (showTopBar) {
        Box(modifier = Modifier.fillMaxWidth().background(surfaceBrandLight).padding(top = 15.dp, bottom = 15.dp)) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth().background(surfaceBrandLight)
                    .fillMaxWidth()
                    .background(surfaceBrandLight)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                val (notificationIcon, profileIcon) = createRefs()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .constrainAs(profileIcon) {
                            start.linkTo(parent.start)
                        }
                ) {

                    GlideImage(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .clickable(enabled = true, onClick = { /* handle click */ }),
                        imageModel = {R.drawable.avatar },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                    )


                    Row(modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,) {

                        Text("Balance",
                            modifier = Modifier.padding(end = 7.dp),
                            style = Typography.bodySmall.copy(fontSize = 18.sp),
                            color = Color.Black)
                        // Icon with animated rotation
                        Image(
                            painter = painterResource(id = R.drawable.arrow_right2),
                            contentDescription = "Notification",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(14.dp)

                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .constrainAs(notificationIcon) {
                            end.linkTo(parent.end)
                        }
                ) {

                    Image(
                        painter = painterResource(R.drawable.offer),
                        contentDescription = "offer",)


                    Image(
                        painter = painterResource(R.drawable.offer_icon),
                        modifier = Modifier.padding(end = 20.dp)
                            .size(25.dp),
                        contentDescription = "offer icon",
                    )
                    Image(
                        painter = painterResource(R.drawable.notification_icon),
                        modifier = Modifier
                            .size(25.dp),
                        contentDescription = "notification icon",
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainToolBar() {
    MainToolBar(
        showTopBar = true,
        toolBarTitle = "Dashboard",
        profilePhoto = "", // placeholder URL
        showBackButton = true,
        onBackClick = {}
    )
}