package com.project.middleman.challengedetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.auth.FirebaseUser
import com.middleman.composables.animation.ConfettiAnimated
import com.middleman.composables.animation.WinnerCupAnimated
import com.middleman.composables.progressbar.CircularProgressBar
import com.middleman.composables.topbar.MainNavigationTopBar
import com.project.middleman.challengedetails.component.AnimatedParticipantCard
import com.project.middleman.challengedetails.component.action.ChallengeActionButtons
import com.project.middleman.challengedetails.component.SummaryRequestInfo
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailUi(
    challenge: Challenge,
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    participants: List<Participant>,
    actionMessage: String,
    winMessage: String,
    creator: Participant?,
    participant: Participant?,
    currentUser: FirebaseUser?,
    onBackClicked: () -> Unit
){

    val showSheet by challengeDetailsViewModel.showSummarySheet.collectAsState()
    val sheetState = rememberModalBottomSheetState()


    Box(modifier = Modifier.fillMaxSize()) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (mainNav, lazyColumn, actions) = createRefs()

            // Top bar (still outside scrollable area)
            MainNavigationTopBar(
                details = "Wager Overview",
                handleBackPressed = { onBackClicked() },
                modifier = Modifier.constrainAs(mainNav){
                    top.linkTo(parent.top)
                }
                    .zIndex(1f) // Keep it above the scroll content
                    .background(white) // Optional: match your theme background
            )

            // Scrollable content
            LazyColumn(
                modifier = Modifier.fillMaxWidth().constrainAs(lazyColumn){
                    top.linkTo(mainNav.bottom, margin = 20.dp)
                }
                    .background(white)
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            )  {
                // Challenge card section
                item {
                    ChallengeCardUi(
                        modifier = Modifier
                            .fillMaxWidth(),
                        challenge = challenge,
                    )

                    Spacer(modifier = Modifier.height(20.dp)) // Space before "Invitations"
                }

                // Invitations list
                items(participants.filter { it.status == "Participant" }) { singleParticipant ->

                    AnimatedParticipantCard(
                        challengeDetailsViewModel,
                        singleParticipant,
                        challenge,
                        creator = creator,
                        currentUser = currentUser
                    )
                    Spacer(modifier = Modifier.height(40.dp)) // Space before "Invitations"
                }

                challenge.winnerDisplayName?.let { name ->
                    if (name.isNotEmpty()) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(text = "Winner: $name",
                                    style = Typography.labelLarge.copy(fontSize = 20.sp))
                                WinnerCupAnimated()
                            }
                            Spacer(modifier = Modifier.height(40.dp)) // Space before "Invitations"
                        }
                    }
                }

                item {
                    CircularProgressBar(
                        currentStake = challenge.payoutAmount,
                        totalStake = if(challenge.status == BetStatus.OPEN.name ||
                            challenge.status == BetStatus.PENDING.name) challenge.payoutAmount *2
                        else challenge.payoutAmount,
                    )
                    Spacer(modifier = Modifier.height(500.dp)) // Space before "Invitations"
                }
            }

            // Bottom action buttons pinned


            Box(
                modifier = Modifier.fillMaxWidth().background(white).constrainAs(actions){
                    bottom.linkTo(parent.bottom)
                }
            ){
                ChallengeActionButtons(
                    showAcceptSummary = {
                        challengeDetailsViewModel.openSummarySheet()
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    winMessage = winMessage,
                    actionMessage = actionMessage,
                    challengeDetailsViewModel = challengeDetailsViewModel,
                    creatorId = creator?.userId,
                    challenge = challenge,
                    participant = participant,
                )
            }

        }

        challenge.winnerDisplayName?.let { name ->
            if (name.isNotEmpty()) {
                ConfettiAnimated(modifier = Modifier.align(Alignment.Center).fillMaxSize())
            }
        }
    }



    SummaryRequestInfo(
        showSheet = showSheet,
        challengeDetailsViewModel = challengeDetailsViewModel,
        sheetState = sheetState,
        challenge = challenge)
}