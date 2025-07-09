package com.middleman.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surface


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBetModalSheet(
    openBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onNewBetClicked: () -> Unit,
    onCreateFromExistingClicked: () -> Unit
) {
    if (openBottomSheet) {
        Dialog(onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false) // <--- important
        ) {

            Box(
                Modifier
                    .fillMaxSize().wrapContentWidth()
                    .padding(horizontal = 0.dp) // or skip it entirely

            ) {

                Box(modifier = Modifier.fillMaxSize().clickable( onClick = onDismissRequest)){}
                Box(
                    modifier = Modifier
                        .padding(horizontal = 0.dp) // or skip it entirely
                        .padding(bottom = 24.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Card(
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp,
                            bottomStart = 28.dp, bottomEnd = 28.dp),
                        elevation = CardDefaults.cardElevation(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 24.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Create bet",
                                    style = MaterialTheme.typography.headlineSmall
                                        .copy(color = colorBlack, fontSize = 20.sp)
                                )
                                IconButton(
                                    onClick = onDismissRequest,
                                    modifier = Modifier
                                        .padding(8.dp) // Outer padding around the button (optional)
                                        .background(color = surface, shape = CircleShape)
                                        .size(36.dp) // Size of the entire circle
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = colorBlack,
                                        modifier = Modifier.padding(6.dp) // Padding to center the icon nicely
                                    )
                                }

                            }

                            Spacer(Modifier.height(20.dp))

                            OptionCard(
                                icon = Icons.Default.Add,
                                iconColor = Color(0xFF6C3EFF),
                                title = "New bet",
                                subtitle = "Create a new bet with a friend on any activity of your choice",
                                onClick = onNewBetClicked
                            )

                            Spacer(Modifier.height(12.dp))

                            OptionCard(
                                icon = Icons.Default.Autorenew,
                                iconColor = Color(0xFF00C853),
                                title = "Create from existing",
                                subtitle = "Create a bet from an existing bet from your past bets",
                                onClick = onCreateFromExistingClicked
                            )
                        }
                    }
                }
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun CreateBetSheetHost() {
    var open by remember { mutableStateOf(true) }

    CreateBetModalSheet(
        openBottomSheet = open,
        onDismissRequest = { open = false },
        onNewBetClicked = { /* handle new bet */ },
        onCreateFromExistingClicked = { /* handle existing bet */ },
    )
}
