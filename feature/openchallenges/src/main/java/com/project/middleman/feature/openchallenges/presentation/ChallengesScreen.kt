package com.project.middleman.feature.openchallenges.presentation

import androidx.compose.runtime.Composable
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.feature.openchallenges.presentation.compoenents.OpenChallengeTabPager

@Composable
fun ChallengesScreen(
    onCardChallengeClick: (Challenge) -> Unit,

    ) {

    val pages = listOf<@Composable () -> Unit>(
        { ChallengeListScreen(
            onCardChallengeClick = onCardChallengeClick
        ) },
        { ChallengeListScreen(
            onCardChallengeClick = onCardChallengeClick
        ) },
        { ChallengeListScreen(
            onCardChallengeClick = onCardChallengeClick
        ) }
    )

    OpenChallengeTabPager(pages = pages)
}