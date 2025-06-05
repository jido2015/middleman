package com.project.middleman.feature.createchallenge.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel

@Composable
fun CreateChallengeScreen(
    onSaveChallenge: () -> Unit,
    viewModel: CreateChallengeViewModel = hiltViewModel()
) {
    val context = LocalContext.current


    //    val id: String = "",
    //    val payoutAmount: Double = 0.0,
    //    val title: String = "",
    //    val description: String = "",
    //    val participant: Map<String, ParticipantProgress> = emptyMap(),
    //    val creator: Map<String, ParticipantProgress> = emptyMap(),
    //    val visibility: String = "public", // or "invite-only or public"
    //    val status: String = "open", // open, active, completed
    //    val winnerId: String? = null,
    //    val createdAt: String = "",
    //    val startDate: String = "",
    //    val endDate: String = ""

    CreateChallengeUi { title, desc, selectedTimeInMillis,stake ->
        viewModel.createChallenge(title, desc, selectedTimeInMillis,stake)
        onSaveChallenge()
    }
}

