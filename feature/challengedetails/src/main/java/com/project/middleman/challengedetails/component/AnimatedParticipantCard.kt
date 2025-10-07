package com.project.middleman.challengedetails.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant

@Composable
fun AnimatedParticipantCard(
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    singleParticipant: Participant,
    challenge: Challenge,
    creator: Participant?
) {
    val density = LocalDensity.current

    var visible by remember { mutableStateOf(false) }

    // Trigger animation only after composition
    LaunchedEffect(singleParticipant.userId) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { with(density) { -40.dp.roundToPx() } } +
                expandVertically(expandFrom = Alignment.Top) +
                fadeIn(initialAlpha = 0.3f, animationSpec = tween(1000)),
        exit = slideOutVertically { -40 } + fadeOut()
    ) {
        InvitationComposeCard(
            challengeDetailsViewModel,
            singleParticipant,
            challenge,
            creator = creator
        )
    }
}
