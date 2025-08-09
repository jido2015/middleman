package com.project.middleman.challengedetails.component

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
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surfaceBrandLighter


@Composable
fun AddParticipantView(
    participant: List<Participant>?,
    creator: String?,
    currentUser: String?,) {

    val participant = participant?.find { it.status == "Participant" }
    Column {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = if (participant != null) "Participant" else "Add a participant to accept challenge",
            style = Typography.labelMedium.copy(color = colorBlack, fontSize = 16.sp),
            fontWeight = FontWeight.Bold
        )

        if (creator == currentUser){
            if (participant == null) {
                Column {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .background(color = surfaceBrandLighter, shape = RoundedCornerShape(100.dp))
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