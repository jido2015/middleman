package com.project.middleman.feature.createchallenge.components

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.middleman.composables.R
import com.middleman.composables.button.CustomButton
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.white
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun PopDialog(
    onDismissRequest: () -> Unit,
    onClickViewWager: () -> Unit,
    onClickInviteParticipants: () -> Unit,
    viewModel: CreateChallengeViewModel? = null
) {
    if (viewModel?.showSheet?.collectAsState()?.value == true) {
        Log.d("SheetCreateChallengeScreen", "Sheet shown")

        Dialog(onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,// <--- important
                dismissOnBackPress = false, //Block the back press
                dismissOnClickOutside = false)
        ) {

            Box(
                Modifier
                    .fillMaxSize().wrapContentWidth()
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
                                .padding(horizontal = 10.dp, vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mark),
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .wrapContentSize().size(50.dp)
                                    .padding(bottom = 18.dp, top = 12.dp),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text ="Your wager has been successfully created!",
                                style = Typography.bodyLarge.copy(fontSize = 25.sp, color = colorBlack,
                                    textAlign = TextAlign.Center),
                                modifier = Modifier.wrapContentSize()
                                    .align(Alignment.CenterHorizontally))

                            Row(
                                modifier = Modifier.padding(top = 40.dp),
                            ) {
                                CustomButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        viewModel.closeSheet()
                                        onClickViewWager()
                                              },
                                    text = "View wager",
                                    borderColor = borderGrey,
                                    textColor = colorBlack,
                                    containerColor = white
                                )
                                Spacer(modifier = Modifier.size(20.dp))
                                CustomButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = { onClickInviteParticipants()},
                                    text = "Invite participants"
                                )
                            }

                        }
                    }

                }
            }

        }
    }
    else{
        Log.d("SheetCreateChallengeScreen", "Sheet not shown")
    }
}



@Preview(showBackground = true)
@Composable
fun CreateBetSheetHost() {
    PopDialog(
        onDismissRequest = {  },
        onClickViewWager = {},
        onClickInviteParticipants = {}
    )
}
