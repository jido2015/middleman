package com.project.middleman.feature.createchallenge.presentation

import androidx.compose.runtime.Composable
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel

@Composable
fun CreateChallengeTitleScreen(
    onSaveChallenge: () -> Unit,
    viewModel: CreateChallengeViewModel
) {

    CreateChallengeTitle(
       onSaveChallenge = onSaveChallenge,
        viewModel = viewModel
    )

}

