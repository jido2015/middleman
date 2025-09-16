package com.middleman.composables.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.middleman.composables.R
import com.middleman.composables.button.ProceedButton
import com.middleman.composables.dot.Dot
import com.middleman.composables.dot.Options
import com.middleman.composables.profile.VSProfileUiView
import com.middleman.composables.profile.ProfileImage
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.common.toTimeAgo
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.lightGrey
import com.project.middleman.designsystem.themes.surface
import com.project.middleman.designsystem.themes.white


@Composable
fun ChallengeCard(
    challenge: Challenge = Challenge(),
) {

    var isRotated by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val star = "\uD83D\uDCB0"

    val creator =  challenge.participant.entries.find {
        it.value.status == "Creator"
    }?.value

    val participant =  challenge.participant.entries.find {
        it.value.status == "Participant"
    }?.value


    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "Arrow Rotation"
    )


    Column(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val ( amount, category, option) = createRefs()

            Box(
                modifier = Modifier.padding(top = 4.dp)
                    .constrainAs(amount) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .background(
                        color = colorAccent,               // background color
                        shape = RoundedCornerShape(30.dp)   // optional rounded corners
                    )
                    .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),  // 👈 space between text & background
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$star $${challenge.payoutAmount}",
                    style = Typography.labelSmall.copy(fontSize = 12.sp),
                    fontWeight = FontWeight.Bold,
                    color = white
                )
            }

            Row(
                modifier = Modifier.constrainAs(option) {
                    top.linkTo(category.bottom, margin = 4.dp)
                    end.linkTo(parent.end)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = borderGrey, // 👈 your border color
                            shape = RoundedCornerShape(6.dp)
                        )
                        .background(
                            color = surface,
                            shape = RoundedCornerShape(6.dp),
                        )
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = challenge.category,
                        style = Typography.labelSmall.copy(fontSize = 12.sp),
                        fontWeight = FontWeight.Bold,
                        color = colorBlack
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Options()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (title, description, image) = createRefs()

            // Title
            Text(
                text = challenge.title,
                style = Typography.labelMedium.copy(fontSize = 16.sp, color = colorBlack),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 10.dp).constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(image.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            ProceedButton(
                imageVector = painterResource(id = R.drawable.arrow_up),
                proceedClicked = { expanded = !expanded
                    isRotated = !isRotated},
                imageModifier = Modifier
                    .graphicsLayer {
                        rotationZ = rotationAngle
                    },
                modifier = Modifier

                    .size(30.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(borderGrey)
                    .constrainAs(image) {
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                        end.linkTo(parent.end)
                    },
                text = "Proceed",
                paddingValues = PaddingValues(10.dp),
                size = 24.dp,
                color = colorBlack
            )


            Column(
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(title.bottom, margin = 5.dp)
                    start.linkTo(title.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = challenge.description,
                        style = Typography.bodyMedium.copy(fontSize = 16.sp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        VSProfileUiView(
            creatorName = creator?.displayName,
            participantName = participant?.displayName
                .takeIf { challenge.status.isNotEmpty() && challenge.status != BetStatus.PENDING.name }
            ,
            participantPhoto = participant?.photoUrl
                .takeIf { challenge.status.isNotEmpty() && challenge.status != BetStatus.PENDING.name }
            ,
            creatorPhoto = creator?.photoUrl,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChallengeCard() {

    ChallengeCard(
        challenge = Challenge(),
    )
}


