package com.project.middleman.challengedetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.middleman.composables.R
import com.middleman.composables.profile.ProfileImage
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.ChallengeStatus
import com.project.middleman.core.common.toTimeAgo
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorGreen
import com.project.middleman.designsystem.themes.white

@Composable
fun InvitationComposeCard(
    viewModel: ChallengeDetailsViewModel? = null,
    participant: Participant? = null,
    challenge: Challenge? = null,
    creator: Participant? = null,
) {

    var columnVisibility by remember { mutableStateOf(false) }

    columnVisibility = challenge?.status == ChallengeStatus.PENDING.name

    if (columnVisibility) {

        Column(
            modifier = Modifier
                .background(white)
                .fillMaxWidth()
        ) {

            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (photo, name, row1, option) = createRefs()

                ProfileImage(
                    modifier = Modifier
                        .constrainAs(photo) {
                            top.linkTo(parent.top, margin = 4.dp)
                            start.linkTo(parent.start)
                        },
                    imageUrl = participant?.photoUrl,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.constrainAs(row1) {
                        top.linkTo(name.bottom)
                        start.linkTo(photo.end, margin = 8.dp)
                    }
                ) {
                    Text(
                        text = participant?.joinedAt?.toTimeAgo() ?: "Not dated",
                        style = Typography.labelSmall.copy(fontSize = 12.sp),
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                }

                Text(
                    modifier = Modifier
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            start.linkTo(photo.end, margin = 8.dp)
                            end.linkTo(option.start, margin = 8.dp)
                            width = Dimension.fillToConstraints // 👈 This is key
                        }
                        .padding(end = 4.dp),

                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("@${participant?.displayName}")
                        }

                        when (challenge?.status) {

                            ChallengeStatus.PENDING.name ->
                                when (viewModel?.localUser?.uid) {
                                    participant?.userId -> {
                                        append(" Your request to join is pending.")

                                    }

                                    creator?.userId -> {
                                        append(" You have a new request waiting for your approval.")
                                    }
                                    else -> {
                                        append(" A new request is pending approval")
                                    }
                                }

                        }
                    },
                    style = Typography.labelMedium.copy(fontSize = 14.sp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.constrainAs(option) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(name.end, margin = 8.dp)
                        end.linkTo(parent.end)
                    }
                ) {

                    if (viewModel?.localUser?.uid == creator?.userId
                        && challenge?.status == ChallengeStatus.PENDING.name
                    ) {


                        // Cancel Request button
                        Box(
                            modifier = Modifier
                                .clickable(onClick = {
                                    viewModel
                                        ?.removeParticipant(
                                            challenge.id,
                                            participant?.userId ?: ""
                                        )
                                })
                                .clip(RoundedCornerShape(16.dp)) // 🟦 Clip the shape after border
                                .background(color = Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.cancel),
                                contentDescription = "Cancel request",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(17.dp),
                                colorFilter = ColorFilter.tint(white) // ← apply tint color here
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Accept Request button
                        Box(
                            modifier = Modifier
                                .clickable(onClick = {
                                    viewModel
                                        ?.acceptParticipantRequest(
                                            challenge.id,
                                            participant?.userId ?: ""
                                        )
                                })
                                .clip(RoundedCornerShape(16.dp)) // 🟦 Clip the shape after border
                                .background(color = colorGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mark),
                                contentDescription = "Accept Request",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(17.dp),
                                colorFilter = ColorFilter.tint(white) // ← apply tint color here
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvitationComposeCardPreview(
) {
    InvitationComposeCard()
}
