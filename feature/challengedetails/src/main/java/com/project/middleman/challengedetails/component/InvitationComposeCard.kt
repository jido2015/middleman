package com.project.middleman.challengedetails.component

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.firebase.auth.FirebaseUser
import com.middleman.composables.R
import com.middleman.composables.profile.ProfileImage
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.common.toTimeAgo
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.white

@Composable
fun InvitationComposeCard(
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    participant: Participant?,
    challenge: Challenge,
    currentUser: FirebaseUser?,
    creator: Participant?,
) {

    Log.d("InvitationComposeCard", "$participant")

    Column(
        modifier = Modifier.background(white).fillMaxWidth()
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (photo, name, row1, option) = createRefs()

            ProfileImage(
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(photo) {
                        top.linkTo(parent.top, margin = 4.dp)
                        start.linkTo(parent.start)
                    },
                imageUrl = participant?.photoUrl,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(row1) {
                    top.linkTo(name.bottom, margin = 4.dp)
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
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(" ${participant?.displayName}")
                    }

                    Log.d("ChallengeStatus", currentUser?.uid ?: "participant")
                    Log.d("ChallengeStatus", creator?.userId ?: "creator")
                    when (challenge.status){

                        BetStatus.PENDING.name   ->
                            when (currentUser?.uid) {
                            participant?.userId -> {
                                append(" Your request to join is pending.")

                            }
                            creator?.userId -> {
                                append(" You have a new request waiting for your approval.")
                            }
                            else -> {
                                append(" has requested to join challenge.")
                            }
                        }

                        BetStatus.ACTIVE.name -> {
                            when (currentUser?.uid) {
                                participant?.userId -> {
                                    append(" Your request to join has been accepted.")

                                }
                                else -> {
                                    append(" has joined this challenge.")

                                }
                            }
                        }
                    }
                },
                style = Typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(photo.end, margin = 8.dp)
                        end.linkTo(option.start, margin = 8.dp)
                        width = Dimension.fillToConstraints // 👈 This is key
                    }
                    .padding(end = 4.dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(option) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(name.end, margin = 8.dp)
                    end.linkTo(parent.end)
                }
            ) {

                if (currentUser?.uid == creator?.userId
                    && challenge.status != BetStatus.ACTIVE.name
                    && challenge.status != BetStatus.COMPLETED.name) {


                    // Cancel Request button
                    Box(
                        modifier = Modifier.clickable( onClick = {
                            challengeDetailsViewModel
                                .removeParticipant(challenge.id,
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

                    Spacer(modifier = Modifier.width(6.dp))

                    // Accept Request button
                    Box(
                        modifier = Modifier.clickable( onClick = {
                            challengeDetailsViewModel
                                .acceptParticipantRequest(challenge.id,
                                    participant?.userId ?: ""
                                )
                        })
                            .clip(RoundedCornerShape(16.dp)) // 🟦 Clip the shape after border
                            .background(color = colorAccent),
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
