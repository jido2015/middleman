package com.project.middleman.feature.createchallenge.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.middleman.core.common.getSelectedTimeInMillis
import com.project.middleman.feature.createchallenge.components.ComposeTimeInputDialogCustom

@Composable
fun CreateChallengeUi(
    onSaveChallenge: (title: String, description: String, selectedTimeInMillis: Long, amount: Double) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showTIme by remember { mutableStateOf(false) }
    var selectedTimeInMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Create a Challenge", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Challenge Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description / Terms") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Stake Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        //Add Input Time picker
        Button(
            onClick = {
                showTIme = true
            },
        ) {
            Text("Post Challenge")
        }

        if (showError) {
            Text("Please fill all fields correctly.", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = {
                val stake = amount.toDoubleOrNull()
                if (title.isNotBlank() && description.isNotBlank() && stake != null) {
                    onSaveChallenge(title, description,selectedTimeInMillis, stake)
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Post Challenge")
        }
    }


    // Show time picker
    if (showTIme) {
        ComposeTimeInputDialogCustom(
            onConfirm = { hour, minute ->
                selectedTimeInMillis = getSelectedTimeInMillis(hour, minute)
                showTIme = false },
            onDismiss = { showTIme = false })
    }

    //Navigate to list of challenges
    fun launch(result: String) {
        Log.d("CreateChallengeScreen", "Challenge Created")
    }

    OnCreateChallengeEvent(launch = {launch(it)})
}

@Preview(showBackground = true)
@Composable
fun CreateChallengeScreenPreview() {
    MaterialTheme {
        CreateChallengeUi(
            onSaveChallenge = { title, description, selectedTimeInMillis, amount ->
                // For preview, we just print to log (won't actually be called here)
                println("Submitted: $title, $description, $amount")
            }
        )
    }
}
