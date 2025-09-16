package com.project.middleman.challengedetails.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.middleman.composables.quickations.MultiActionButton
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorGreen
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.lightGrey
import com.project.middleman.designsystem.themes.surfaceBrandLighter
import com.project.middleman.designsystem.themes.white

@Composable
fun ChallengeActionButtons(
    showAcceptSummary: () -> Unit,
    modifier: Modifier,
    actionMessage: String,
    winMessage : String,
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    creatorId: String?,
    challenge: Challenge,
    participant: Participant?,
) {
    Column(
        modifier = modifier.background(white)

    ) {
        HorizontalDivider(
            color = borderGrey,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = white)
        )


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 20.dp),
            text = actionMessage,
            style = Typography.labelSmall.copy(fontSize = 12.sp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(20.dp))


        AddParticipantView(
            creatorId,
            challengeDetailsViewModel.localUser?.uid,
            challenge)


        if (challengeDetailsViewModel.localUser?.uid == creatorId ||
            challengeDetailsViewModel.localUser?.uid == participant?.userId){
            if( challenge.status == BetStatus.PARTICIPANT_WINS.name ||
                challenge.status == BetStatus.CREATOR_WINS.name){
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(surfaceBrandLighter)
                        .padding(16.dp)
                ) {
                    // Row content here
                    Image(
                        painter = painterResource(id = R.drawable.notification_icon),
                        contentDescription = "Notification",
                        modifier = Modifier
                            .padding( end = 10.dp)
                            .size(17.dp),
                        colorFilter = ColorFilter.tint(deepColorAccent)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = winMessage,
                        style = Typography.labelSmall.copy(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

            }

        }

        // Creator Actions
        when (challengeDetailsViewModel.getCurrentUser()?.uid) {
            creatorId-> {
                // Creator Actions
                Log.d("Whois", "Creator Message Reached")

                CreatorActionButton(challenge, challengeDetailsViewModel)

            }
            // Participant Actions
            participant?.userId -> {
                Log.d("Whois", "Participant Message Reached")

                ParticipantActionButton(
                    challenge,
                    challengeDetailsViewModel,
                    participant,)
            }

            else -> {
                // Viewers Actions
                Log.d("Whois", "Viewer Message Reached")

                ViewersActionButton(
                    showAcceptSummary = showAcceptSummary,
                    challenge = challenge,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        MultiActionButton()


    }
}