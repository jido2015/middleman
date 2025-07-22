package com.project.middleman.feature.createchallenge.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.button.CustomButton
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.feature.createchallenge.components.LabelValueRow
import com.project.middleman.feature.createchallenge.components.LabeledCheckbox
import com.project.middleman.feature.createchallenge.components.PopDialog
import com.project.middleman.feature.createchallenge.components.calculateFee
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel

@Composable
fun ChallengeSummaryScreen(
    viewModel: CreateChallengeViewModel,
    createWagerButton: () -> Unit = {}
){
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val fee = calculateFee(viewModel.stake* 2)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {

        Image(
            painter = painterResource(id = com.middleman.composables.R.drawable.wishapp),
            contentDescription = "Icon",
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 18.dp, top = 12.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            "Wager $${viewModel.stake} on ${viewModel.title}",
            style = Typography.bodyLarge.copy(fontSize = 25.sp, color = colorBlack),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        LabelValueRow(
            label = "Bet type",
            value = "Head to Head"
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = borderGrey
        )

        LabelValueRow(
            label = "Category",
            value = viewModel.category
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = borderGrey
        )

        LabelValueRow(
            label = "Amount",
            value = "$${viewModel.stake}"
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = borderGrey
        )

        LabelValueRow(
            label = "Fees",
            value = "$$fee"
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = borderGrey
        )

        Spacer(modifier = Modifier.height(28.dp))
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (constraintTotal, constraintTotalValue, constraintCharges)
            = createRefs()

            Text(
                text = "Total Wager (Both Participants)",
                style =  Typography.labelMedium.copy(fontSize = 16.sp, color = colorBlack,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(constraintTotal) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)

                })

            Text(
                text = "$${(viewModel.stake*2) - fee}",
                style =  Typography.labelMedium.copy(fontSize = 16.sp,
                    color = colorBlack, fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(constraintTotalValue) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)

                })
            Text(
                text = "Incl. $$fee fee",
                style =  Typography.labelMedium.copy(
                    fontSize = 16.sp, color = colorBlack),
                modifier = Modifier.constrainAs(constraintCharges) {
                    top.linkTo(constraintTotalValue.bottom)
                    end.linkTo(parent.end)

                })
        }
        Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom
        LabeledCheckbox(
            label = "I understand that I might need to provide verifiable" +
                    " evidence in-order to confirm I won this wager.",
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            onClick =
            {
                if (isChecked){
                    viewModel.createChallenge()
                    Log.d("CreateChallengeScreen", "Challenge Created From Button")

                }else{
                    Toast.makeText(context, "Please accept the terms", Toast.LENGTH_SHORT).show()
                }

            }, text = "Create wager")
    }


    // Your dialog
    PopDialog(
        openBottomSheet = showDialog,
        onDismissRequest = { showDialog = false },
        onClickViewWager = {
            showDialog = false
            createWagerButton()
            // Navigate to view wager screen or perform action
        },
        onClickInviteParticipants = {
           // showDialog = false
            // Trigger invite action
        }
    )



    //    //Navigate to list of challenges
    fun launch(result: String) {
        //
        showDialog = true
        Log.d("CreateChallengeScreen", "Challenge Created")
    }
//
    OnCreateChallengeEvent(viewModel, launch = { launch(it) })
}

@Preview(showBackground = true)
@Composable
fun ChallengeSummaryScreenPreview(){
    ChallengeSummaryScreen(
        viewModel = hiltViewModel()
    )
}