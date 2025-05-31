package com.project.middleman.feature.openchallenges.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun ChallengeDialog(showDialog: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit){
    //TODO: Create Dialog to accept challenge
    if (showDialog){
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Accept Challenge") },
            text = { Text(text = "Are you sure you want to accept this challenge?") },
            confirmButton = {

                //TODO: Add confirm button
                TextButton(onClick = onConfirm) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
