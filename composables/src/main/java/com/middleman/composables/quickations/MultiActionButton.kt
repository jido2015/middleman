package com.middleman.composables.quickations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.project.middleman.designsystem.themes.DeepGrey
import com.project.middleman.designsystem.themes.Grey
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.lightGrey

@Composable
fun MultiActionButton(
) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.width(73.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey)
                .border( // Add this modifier for the border
                    width = 1.dp, // Specify the border width
                    color = lightGrey, // Specify the border color
                    shape = RoundedCornerShape(100.dp) // Match the background shape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "Notification",
                modifier = Modifier
                    .padding(10.dp)
                    .size(17.dp),
            )
        }

        Row(
            modifier = Modifier.width(73.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey)
                .border( // Add this modifier for the border
                    width = 1.dp, // Specify the border width
                    color = lightGrey, // Specify the border color
                    shape = RoundedCornerShape(100.dp) // Match the background shape
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier = Modifier.size(17.dp),
                painter = painterResource(id = R.drawable.people),
                contentDescription = "People",
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "1",
                style = Typography.labelMedium.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold)
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey)
                .border( // Add this modifier for the border
                    width = 1.dp, // Specify the border width
                    color = lightGrey, // Specify the border color
                    shape = RoundedCornerShape(100.dp) // Match the background shape
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier = Modifier.size(17.dp),
                painter = painterResource(id = R.drawable.chat),
                contentDescription = "Chat",
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "100",
                style = Typography.labelMedium.copy(fontSize = 12.sp,  fontWeight = FontWeight.Bold)
            )
        }

        Row(
            modifier = Modifier.width(73.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey)
                .border( // Add this modifier for the border
                    width = 1.dp, // Specify the border width
                    color = lightGrey, // Specify the border color
                    shape = RoundedCornerShape(100.dp) // Match the background shape
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier = Modifier.size(17.dp),
                painter = painterResource(id = R.drawable.help),
                contentDescription = "Chat",
            )
        }
        Row(
            modifier = Modifier.width(73.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey)
                .border( // Add this modifier for the border
                    width = 1.dp, // Specify the border width
                    color = lightGrey, // Specify the border color
                    shape = RoundedCornerShape(100.dp) // Match the background shape
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier = Modifier.size(17.dp),
                painter = painterResource(id = R.drawable.share),
                contentDescription = "Chat",
            )
        }

    }
}



@Preview(showBackground = true)
@Composable
fun MultiActionButtonPreview() {
    MultiActionButton()
}
