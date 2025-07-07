package com.middleman.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.middleman.composables.button.ProceedButton
import com.middleman.composables.progressbar.ProgressBarUI
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surface
import com.project.middleman.designsystem.themes.white

@Composable
fun VerificationProgress(
    onProceedClicked: () -> Unit
) {
    var currentProgress by remember { mutableIntStateOf(2) }

        ConstraintLayout(Modifier.fillMaxWidth().
        padding(horizontal = 20.dp, vertical = 4.dp).clip(
            RoundedCornerShape(10.dp)
        ).background(surface)
        ) {

            val (title, progressBar, proceedBtn) = createRefs()

            Text("Complete verification and start withdrawing"
                , style = Typography.titleSmall.copy(fontSize = 12.sp)
                    .copy() ,
                modifier = Modifier.padding(10.dp).constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                color = colorBlack)

            Card(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    .border(2.dp, borderGrey, RoundedCornerShape(100.dp))
                    .fillMaxWidth().constrainAs(progressBar) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ){

                Row (modifier = Modifier
                    .background(white),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ){
                    Text("2/4", style = Typography.titleSmall
                        .copy(fontSize = 12.sp),
                        color = colorAccent,
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp,
                            bottom = 4.dp, start = 8.dp)
                    )
                    ProgressBarUI(currentProgress,
                        modifier = Modifier.padding(end = 8.dp))
                }
            }

            Row (modifier = Modifier.padding(10.dp).fillMaxWidth().constrainAs(proceedBtn) {
                top.linkTo(progressBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Text("Verify and withdraw earnings",
                    style = Typography.bodySmall.copy(fontSize = 12.sp),
                    color = colorBlack)

                ProceedButton(
                    proceedClicked = onProceedClicked,
                    modifier = Modifier
                        .padding()
                        .clip(RoundedCornerShape(100.dp)),
                    text = "Proceed",
                    paddingValues = PaddingValues(0.dp),
                    size = 16.dp,
                    color = Color.Black)
            }
        }

}


@Composable
@Preview(showBackground = true)
fun PreviewVerificationProgress() {
    MaterialTheme {
        VerificationProgress(onProceedClicked = {})
    }

}