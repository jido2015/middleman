package com.project.middleman.challengedetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.middleman.composables.button.CustomButton
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DisputeScreenBottomSheet(
    sheetState: SheetState,
    viewModel: ChallengeDetailsViewModel? = null, // nullable
) {
    val scope = rememberCoroutineScope()
    val disputeDescription = remember { mutableStateOf("") }


    if (viewModel?.showDisputeModalSheet?.collectAsState()?.value  == true) {
    ModalBottomSheet(
        containerColor = White,
        onDismissRequest = { viewModel.closeDisputeModalSheet() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .imePadding(), // ✅ makes room when keyboard shows
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PickDisputeFiles(disputeDescription)

            Spacer(modifier = Modifier.weight(1f)) // pushes button down

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        viewModel.closeDisputeModalSheet()
                    }
                },
                text = "Submit dispute"
            )
        }


    }
      }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewDisputeScreenBottomSheet() {

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
    DisputeScreenBottomSheet(
        sheetState = sheetState,
        // In preview, you can either pass a stub or overload your composable to not require VM
    )
}
