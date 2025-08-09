package com.middleman.feature.notification

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.middleman.composables.button.ProceedButton
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.deepColorAccent
import com.middleman.composables.R
import com.project.middleman.designsystem.themes.SetStatusBarStyle
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.white

@Composable
fun AnimatedNotificationBar(
    isNotificationBarSheet: Boolean,
    modifier: Modifier = Modifier,
    isMessageVisible: Boolean,
    isRotated: Boolean,
    onToggle: () -> Unit,
    onProceedClicked: () -> Unit
) {
    val density = LocalDensity.current

    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "Arrow Rotation"
    )

    Column(modifier = modifier.padding(top = 60.dp,  bottom = 12.dp)) {

        if (isNotificationBarSheet){
            SetStatusBarStyle(white, useDarkIcons = false)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Text(
                text = "Derahn invited you to a match and more details about the game",
                style = Typography.bodySmall,
                color = Color.White,
                modifier = Modifier.widthIn(max = 200.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                painter = painterResource(id = R.drawable.arrow_up),
                contentDescription = "Notification",
                tint = Color.White,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onToggle() }
                    .size(24.dp)
                    .graphicsLayer {
                        rotationZ = rotationAngle
                    }
            )
        }

        AnimatedVisibility(
            visible = isMessageVisible,
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
                        .padding(20.dp)
                        .align(Alignment.Center)
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorAccent)
                            .padding(12.dp)
                    ) {
                        val (title, stake, close, enter) = createRefs()

                        Text(
                            text = "FC25 game match on playstation.",
                            style = Typography.bodyMedium.copy(fontSize = 16.sp),
                            modifier = Modifier.constrainAs(title) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                            color = Color.White
                        )

                        Text(
                            text = "$100 staked presently.",
                            style = Typography.bodyMedium.copy(fontSize = 12.sp),
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 12.dp)
                                .constrainAs(stake) {
                                    top.linkTo(title.bottom)
                                    start.linkTo(parent.start)
                                },
                            color = Color.White
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { onToggle() }
                                .size(24.dp)
                                .constrainAs(close) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                }
                        )

                        ProceedButton(
                            proceedClicked = onProceedClicked,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(deepColorAccent)
                                .constrainAs(enter) {
                                    top.linkTo(stake.bottom)
                                    start.linkTo(parent.start)
                                    bottom.linkTo(parent.bottom)
                                },
                            text = "Proceed",
                            paddingValues = PaddingValues(10.dp),
                            size = 24.dp,
                            color = Color.White
                        )
                    }
                }
            }
        } } else{

            SetStatusBarStyle(colorBlack, useDarkIcons = true)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewAnimatedNotificationBar() {
    // Local states to simulate visibility and rotation in the preview
    var visible by remember { mutableStateOf(true) }
    var isRotated by remember { mutableStateOf(true) }

    AnimatedNotificationBar(
        isNotificationBarSheet = true,
        modifier = Modifier,
        isMessageVisible = visible,
        isRotated = isRotated,
        onToggle = {
            visible = !visible
            isRotated = !isRotated
        },
        onProceedClicked = {
            // No-op for preview
        }
    )
}
