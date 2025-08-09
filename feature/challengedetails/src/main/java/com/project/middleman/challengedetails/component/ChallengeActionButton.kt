package com.project.middleman.challengedetails.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.white

@Composable
fun ChallengeActionButtons(
    modifier: Modifier,
    actionMessage: String,
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    creatorId: String?,
    challenge: Challenge,
    participant: Participant?,
    onAcceptChallenge: () -> Unit
) {
    Column(
        modifier = modifier

    ) {
        HorizontalDivider(
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
            style = Typography.labelSmall.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(20.dp))

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

                ParticipantActionButton(challenge, challengeDetailsViewModel, participant)
            }

            else -> {
                // Viewers Actions
                Log.d("Whois", "Viewer Message Reached")

                ViewersActionButton(
                    challengeDetailsViewModel = challengeDetailsViewModel,
                    challenge = challenge,
                    onAcceptChallenge = onAcceptChallenge
                )
            }
        }
    }
}