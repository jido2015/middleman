package com.project.middleman.challengedetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.middleman.composables.R
import com.middleman.composables.button.CustomButton
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.white


@Composable
fun DisputeDialog(
    onDismissRequest: () -> Unit,
    onDisputeScreen: () -> Unit,
    viewModel: ChallengeDetailsViewModel? = null
) {

    if (viewModel?.showDisputeDialog?.collectAsState()?.value == true) {

        val doNotShowLater = remember { mutableStateOf(false) }

        Dialog(onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,// stretch Edge to Edge
                )
        ) {

            Box(
                Modifier
                    .fillMaxSize()
                    .wrapContentWidth()
                    .padding(horizontal = 0.dp) // or skip it entirely

            ) {

                Box(modifier = Modifier.fillMaxSize()){}
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
                                .padding(horizontal = 20.dp, vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.cancel),
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .padding(bottom = 18.dp, top = 12.dp),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text ="What is dispute resolution?",
                                style = Typography.bodyLarge.copy(fontSize = 16.sp, color = colorBlack))

                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(com.project.middleman.challengedetails.R.string.dispute_meaning),
                                style = Typography.labelSmall.copy(fontSize = 14.sp))

                            DisputeCheckbox( visibilityState = doNotShowLater,modifier = Modifier.fillMaxWidth().padding(top = 20.dp))
                            Row(
                                modifier = Modifier.padding(top = 40.dp),
                            ) {
                                CustomButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                     onDismissRequest()
                                    },
                                    text = "Cancel",
                                    borderColor = borderGrey,
                                    textColor = colorBlack,
                                    containerColor = white
                                )
                                Spacer(modifier = Modifier.size(20.dp))
                                CustomButton(
                                    containerColor = Color.Red,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onDisputeScreen()},
                                    text = "Dispute"
                                )
                            }

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
    DisputeDialog(
        onDismissRequest = {  },
        onDisputeScreen = {},
    )
}
