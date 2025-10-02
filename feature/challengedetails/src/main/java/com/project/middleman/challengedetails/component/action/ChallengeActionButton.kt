package com.project.middleman.challengedetails.component.action

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.middleman.composables.quickations.MultiActionButton
import com.project.middleman.challengedetails.component.AddParticipantView
import com.project.middleman.challengedetails.component.dipsute.DisputeDialog
import com.project.middleman.challengedetails.component.dipsute.DisputeScreenBottomSheet
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.surfaceBrandLighter
import com.project.middleman.designsystem.themes.white

@OptIn(ExperimentalMaterial3Api::class)
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

    val disputeSheetState = rememberModalBottomSheetState()

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

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            text = actionMessage,
            style = Typography.labelSmall.copy(fontSize = 12.sp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))


        AddParticipantView(
            creatorId,
            challengeDetailsViewModel.localUser?.uid,
            challenge
        )


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

                Spacer(modifier = Modifier.height(5.dp))

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

        MultiActionButton()
    }

    // Dispute Dialog
    DisputeDialog(
        onDismissRequest = {
            challengeDetailsViewModel.closeDisputeDialog()
        },
        onDisputeScreen = {
            challengeDetailsViewModel.closeDisputeDialog()
            challengeDetailsViewModel.openDisputeModalSheet()

            // Navigate to view wager screen or perform action
        },
        viewModel = challengeDetailsViewModel,

        )

    DisputeScreenBottomSheet(
        sheetState = disputeSheetState,
        viewModel = challengeDetailsViewModel,
    )
}