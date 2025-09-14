package com.project.middleman.challengedetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surfaceBrandLighter
import com.project.middleman.designsystem.themes.white


@Composable
fun AddParticipantView(
    creator: String?,
    currentUser: String?,
    challenge: Challenge = Challenge()
) {

    Column {

        if (creator == currentUser){
            if (challenge.participant.entries.find { it.value.status == "Participant" }?.value == null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                        .background(
                            color = white,
                            shape = RoundedCornerShape(100.dp)
                        )
                        .border( // Add this modifier for the border
                            width = 1.dp, // Specify the border width
                            color = borderGrey, // Specify the border color
                            shape = RoundedCornerShape(100.dp) // Match the background shape
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {

                    IconButton(
                        onClick = { /* TODO: handle click */ },
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_person),
                            contentDescription = "Add participant",
                            tint = colorBlack,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Text(
                        text = "Add Participant",
                        color = colorBlack,
                        style = Typography.titleSmall.copy(fontSize = 14.sp),
                        modifier = Modifier.padding(end = 8.dp) // Space between text and icon
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantPreview(){
    AddParticipantView(
        "creator",
        "currentUser",
        Challenge()
    )
}