package com.middleman.feature.notification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.deepColorAccent


@Composable
fun AnimatedNotificationBar(modifier: Modifier = Modifier, onProceedClicked: () -> Unit) {
    var visibility by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    var isRotated by remember { mutableStateOf(false) }

    // Animate the rotation of the icon
    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "Arrow Rotation"
    )

    Column(modifier = modifier) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 12.dp)
                    .background(Color.Black),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Box with text
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorAccent)
                ) {
                    Text(
                        text = "Head 2 Head",
                        style = Typography.bodySmall,
                        modifier = Modifier.padding(10.dp),
                        color = Color.White
                    )
                }

                // Middle Text with fixed max width
                Text(
                    text = "Derahn invited you to a match and more details about the game",
                    style = Typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.widthIn(max = 200.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Icon with animated rotation
                Icon(
                    painter = painterResource(id = R.drawable.arrow_up),
                    contentDescription = "Notification",
                    tint = Color.White,
                    modifier = Modifier.clip(CircleShape).clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // Let Material 3 apply default ripple
                    ) {
                        isRotated = !isRotated // Toggle on click
                        visibility = !visibility
                    }
                        .size(24.dp)
                        .graphicsLayer {
                            rotationZ = rotationAngle
                        }
                )
            }


        AnimatedVisibility(
            visible = visibility,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = slideOutVertically {
                with(density) { -40.dp.roundToPx() }
            } + shrinkVertically(
                shrinkTowards = Alignment.Top
            ) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Black)
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 12.dp)

                        .align(Alignment.Center)
                ) {
                    ConstraintLayout (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorAccent)
                            .padding(12.dp)
                    ){
                        val (title, stake, close , enter ) = createRefs()

                        Text(
                            text = "FC25 game match on playstation.",
                            style = Typography.bodyMedium.copy(fontSize = 18.sp),
                            modifier = Modifier.constrainAs(title) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                            color = Color.White
                        )
                        Text(
                            text = "$100 staked presently.",
                            style = Typography.bodyMedium.copy(fontSize = 18.sp),
                            modifier = Modifier.padding(top = 12.dp)
                                .constrainAs(stake) {
                                    top.linkTo(title.bottom)
                                    start.linkTo(parent.start)
                                },
                            color = Color.White
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Notification",
                            tint = Color.White,
                            modifier = Modifier.clip(CircleShape)
                                .constrainAs(close) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                }.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null // Let Material 3 apply default ripple
                            ) {
                                    isRotated = !isRotated // Toggle on click
                                    visibility = !visibility
                            }.size(24.dp)

                        )
                        ProceedButton(onProceedClicked,
                            modifier = Modifier.constrainAs(enter) {
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            })

                    }
                }
            }
        }
    }
}

@Composable
fun ProceedButton(proceedClicked: () -> Unit, modifier: Modifier){

    Row(modifier = modifier.clip(RoundedCornerShape(100.dp)).background(deepColorAccent)) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Proceed Button",
            tint = Color.White,
            modifier = Modifier.clip(CircleShape).padding(10.dp).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Let Material 3 apply default ripple
            ) {
                proceedClicked()
            }.size(24.dp)

        )
    }
}
