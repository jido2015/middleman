package com.project.middleman.challengedetails.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.R
import com.middleman.composables.textfield.BorderlessTextField
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surface
import com.skydoves.landscapist.InternalLandscapistApi

@OptIn(InternalLandscapistApi::class)
@Composable
fun PickDisputeFiles(disputeDescription: MutableState<String>) {

    val focusRequester = remember { FocusRequester() }
    val selectedMedia = remember { mutableStateListOf<Uri>() }

    // Registers a photo picker activity launcher in single-select mode.
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uri ->
        selectedMedia.addAll(uri)

        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        Log.d("PhotoPicker", "Selected URI: $uri")
    }

    Column {
        Text(modifier= Modifier.fillMaxWidth(),text = "Upload proof of claim",
            style = Typography.titleMedium.copy(fontSize = 14.sp,
                color = colorBlack, textAlign = TextAlign.Center))
        Spacer(modifier = Modifier.size(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(true, onClick = {
                    pickMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageAndVideo
                        )
                    )
                }),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(0.5.dp, borderGrey),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            if (selectedMedia.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(surface)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = "My image",
                        modifier = Modifier
                            .size(56.dp)
                            .padding(bottom = 12.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "Upload proof of claim",
                        style = Typography.titleMedium.copy(fontSize = 14.sp, color = colorBlack)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "JPEG,PNG and MP4 only",
                        style = Typography.labelMedium.copy(fontSize = 12.sp, color = colorBlack)
                    )

                }
            } else {
                MediaGrid(uris = selectedMedia)
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = borderGrey
        )

        Spacer(modifier = Modifier.size(16.dp))

        // Challenge description input
        BorderlessTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            text = disputeDescription.value,
            onValueChange = { newText ->
                disputeDescription.value = newText
            },
            placeholder = "Share your dispute here...",
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            singleLine = false,
            maxLines = 5
        )
        Spacer(modifier = Modifier.size(100.dp))

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewPickDisputeFiles() {
    PickDisputeFiles(disputeDescription = remember { mutableStateOf("") })
}