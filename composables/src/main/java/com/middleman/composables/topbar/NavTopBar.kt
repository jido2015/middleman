package com.middleman.composables.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surface
import com.middleman.composables.R
import com.project.middleman.designsystem.themes.Typography

@Composable
fun MainNavigationTopBar(
    details: String,
    handleBackPressed : () -> Unit,
    modifier: Modifier = Modifier,
) {
        Column(
            modifier = modifier.padding(bottom = 20.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            ) {

                val (back, share, more, title) = createRefs()
                IconButton(
                    onClick = {handleBackPressed()},
                    modifier = Modifier.constrainAs(back) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                        .background(color = surface, shape = RoundedCornerShape(15.dp))
                        .size(36.dp) // Size of the entire circle
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Close",
                        tint = colorBlack,
                        modifier = Modifier.padding(10.dp) // Padding to center the icon nicely
                    )
                }


                Text(
                    modifier = Modifier.padding(top = 10.dp).constrainAs(title) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                    text = details,
                    style = Typography.labelSmall.copy(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center // ensures visual centering
                )


                IconButton(
                    onClick = {},
                    modifier = Modifier.constrainAs(share) {
                        end.linkTo(more.start, margin = 20.dp)
                        top.linkTo(parent.top)
                    }
                        .background(color = surface, shape = RoundedCornerShape(15.dp))
                        .size(36.dp) // Size of the entire circle
                ) {
                    Icon(
                        painter = painterResource(R.drawable.share),
                        contentDescription = "Help",
                        tint = colorBlack,
                        modifier = Modifier.padding(10.dp) // Padding to center the icon nicely
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier.constrainAs(more) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                        .background(color = surface, shape = RoundedCornerShape(15.dp))
                        .size(36.dp) // Size of the entire circle
                ) {
                    Icon(
                        painter = painterResource(R.drawable.more),
                        contentDescription = "Help",
                        tint = colorBlack,
                        modifier = Modifier.padding(10.dp) // Padding to center the icon nicely
                    )
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigationTopBar() {
    MainNavigationTopBar(
        details = "Wager Overview",
        handleBackPressed = {},
    )
}