package com.project.middleman.challengedetails.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surfaceBrandLighter


@Composable
fun AddParticipantView(
    creator: String?,
    currentUser: String?,
    challenge: Challenge
) {

    Column {
        Log.d("BetStatusInItem", "creator: $creator, currentUser: ${challenge.status}")
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = if(challenge.status == BetStatus.OPEN.name){
                ""
            } else {
                "Opponent"
            },
            style = Typography.labelMedium.copy(color = colorBlack, fontSize = 16.sp),
            fontWeight = FontWeight.Bold
        )



        if (creator == currentUser){
            if (challenge.participant.entries.find { it.value.status == "Participant" }?.value == null) {
                Column {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        text = "Add Participant",
                        style = Typography.labelMedium.copy(color = colorBlack, fontSize = 16.sp),
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .background(
                                color = surfaceBrandLighter,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .size(70.dp) // Size of the entire circle
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_person),
                            contentDescription = "Add participant",
                            tint = colorBlack,
                            modifier = Modifier.padding(10.dp) // Padding to center the icon nicely
                        )
                    }

                }
            }
        }

    }
}