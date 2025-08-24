package com.middleman.composables.quickations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.middleman.composables.button.CustomButton
import com.project.middleman.designsystem.themes.*

@Composable
fun QuickActions(
    containerColor: Color = colorAccent,
    modifier: Modifier,
    btnTextColor: Color = white,
    btnText: String = "Join Wager",
    onButtonClick: () -> Unit = {},
    shouldActionBtnVisible: Boolean = false,
    enableButton: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (shouldActionBtnVisible){
            CustomButton(
                textColor = btnTextColor,
                enableButton = enableButton,
                containerColor = containerColor,
                text = btnText,
                onClick = {onButtonClick()},
                modifier = Modifier.weight(3f).padding(end = 10.dp)
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey),
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
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = borderGrey)
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

        // Pushes the final image to the end
  //      androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

//        Image(
//            modifier = Modifier.size(17.dp),
//            painter = painterResource(id = R.drawable.outline_share_24),
//            contentDescription = "End Chat Icon",
//        )
    }
}



@Preview(showBackground = true)
@Composable
fun QuickActionsPreview() {
    QuickActions(modifier = Modifier.padding(16.dp))
}
