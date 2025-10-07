package com.project.middleman.challengedetails.component.dipsute

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.middleman.composables.button.CustomButton
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.upload.worker.enqueueMultipleEvidenceUploads
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DisputeScreenBottomSheet(
    sheetState: SheetState,
    viewModel: ChallengeDetailsViewModel? = null, // nullable
) {
    val scope = rememberCoroutineScope()
    val disputeNote = remember { mutableStateOf("") }
    val selectedMedia = remember { mutableStateListOf<Uri>() }

    val context = LocalContext.current
    val latestChallenge = viewModel?.latestChallenge?.collectAsState()
    if (viewModel?.showDisputeModalSheet?.collectAsState()?.value == true) {
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
                PickDisputeFiles(
                    selectedMedia = selectedMedia,
                    disputeDescription = disputeNote
                )

                Spacer(modifier = Modifier.weight(1f)) // pushes button down

                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if (selectedMedia.isNotEmpty()){
                            Log.d("DisputeUserId", "${viewModel.localUser?.uid}")
                            Log.d("DisputeChallengeId", "${latestChallenge?.value?.id}")
                            enqueueMultipleEvidenceUploads(
                                context, challengeId = latestChallenge?.value?.id.toString(),
                                userId = viewModel.localUser?.uid!!,
                                uris = selectedMedia.toList(),
                                disputeNote = disputeNote.toString(),
                                challengeStatus = viewModel.challengeStatus,
                            )
                        }

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
