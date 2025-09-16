package com.project.middleman.feature.openchallenges.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.middleman.composables.card.ChallengeCard
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.borderGrey

@Composable
fun ChallengeCardItem(
    challenge: Challenge = Challenge(),
    onChallengeClick: (Challenge) -> Unit
) {

    Card(
        modifier = Modifier.clickable{
            onChallengeClick(challenge)
        }
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(0.5.dp, borderGrey),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            ChallengeCard(
                challenge = challenge,
            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewChallengeCardItem() {
}

