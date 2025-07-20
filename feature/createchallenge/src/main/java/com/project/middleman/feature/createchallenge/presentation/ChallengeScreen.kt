package com.project.middleman.feature.createchallenge.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel

@Composable
fun CreateChallengeTitleScreen(
    onSaveChallenge: () -> Unit,
    viewModel: CreateChallengeViewModel
) {

    CreateChallengeUi(
       onSaveChallenge = onSaveChallenge,
        viewModel = viewModel
    )

}

