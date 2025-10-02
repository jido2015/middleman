package com.project.middleman.challengedetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.middleman.composables.button.CustomButton
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SummaryRequestInfo(
    challenge: Challenge = Challenge(),
    showSheet: Boolean,
    sheetState: SheetState,
    challengeDetailsViewModel: ChallengeDetailsViewModel? = null, // nullable
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val fess = challenge.payoutAmount * 0.05

    if (showSheet) {
        ModalBottomSheet(
            containerColor = White,
            onDismissRequest = { challengeDetailsViewModel?.closeSummarySheet() },
            sheetState = sheetState
        ) {
            Column(Modifier

                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(
                    "$${challenge.payoutAmount}",
                    style = Typography.titleLarge.copy(fontSize = 25.sp, color = colorBlack)
                )

                Text(
                    modifier = Modifier.padding(bottom =32.dp),
                    text = "Total stake  (Both Participants)",
                    style =  Typography.labelSmall.copy(fontSize = 14.sp, color = colorBlack))

                LabelValueRow(
                    label = "Amount Added",
                    value = "$${challenge.payoutAmount/2}"
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 1.dp,
                    color = borderGrey
                )

                LabelValueRow(
                    label = "Fees",
                    value = "$$fess"
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 1.dp,
                    color = borderGrey
                )
                ConstraintLayout(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)) {
                    val (constraintTotal, constraintTotalValue, constraintCharges)
                            = createRefs()

                    Text(
                        text = "Total",
                        style =  Typography.labelMedium.copy(fontSize = 16.sp, color = colorBlack,
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier.constrainAs(constraintTotal) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)

                        })

                    Text(
                        text = "$${challenge.payoutAmount - fess}",
                        style =  Typography.labelMedium.copy(fontSize = 16.sp,
                            color = colorBlack, fontWeight = FontWeight.Bold),
                        modifier = Modifier.constrainAs(constraintTotalValue) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)

                        })
                    Text(
                        text = "Excl. $fess fee",
                        style =  Typography.labelMedium.copy(
                            fontSize = 16.sp, color = colorBlack),
                        modifier = Modifier.constrainAs(constraintCharges) {
                            top.linkTo(constraintTotalValue.bottom)
                            end.linkTo(parent.end)

                        })
                }

                Text(
                    modifier = Modifier.padding(bottom =32.dp),
                    text = "By joining this wager, I agree to the terms and conditions and dispute " +
                            "resolution policy for whisper. You also agree to the terms set" +
                            " by the wager creator",
                    style =  Typography.labelSmall.copy(fontSize = 14.sp, color = colorBlack, textAlign = TextAlign.Center))


                Spacer(Modifier.height(8.dp))
                CustomButton(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
                    onClick = {
                        challengeDetailsViewModel?.onChallengeAccepted(challenge)
                        scope.launch { sheetState.hide()
                            challengeDetailsViewModel?.closeSummarySheet()}
                    },
                    text = "Join wager")
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SummaryRequestInfoPreview() {

    // Sheet state for preview
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Force it open in preview
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch { sheetState.show() }
    }

    // Render your composable
    SummaryRequestInfo(
        showSheet = true, // force true in preview
        sheetState = sheetState,
        // In preview, you can either pass a stub or overload your composable to not require VM
    )
}
