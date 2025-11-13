package com.example.measuremate.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PhysiQDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    title: String,
    confirmButtonText: String = "Yes",
    dismissButtonText: String = "Cancel",
    body: @Composable () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDialogDismiss: () -> Unit
) {
    if(isOpen) {
        AlertDialog(
            modifier = modifier,
            text = body,
            title = { Text( text = title) },
            onDismissRequest = { onDialogDismiss() },
            confirmButton = {
                TextButton( onClick = { onConfirmButtonClick() } ) {
                    Text( text = confirmButtonText )
                }
            },
            dismissButton = {
                TextButton( onClick = { onConfirmButtonClick() } ) {
                    Text( text = dismissButtonText )
                }
            }
        )
    }
}

@Preview
@Composable
private fun PhysiQDialogPreview() {
    PhysiQDialog (
        isOpen = true,
        title = "Login anonymously?",
        onDialogDismiss = { },
        onConfirmButtonClick = { },
        body = {
            Text(
                text = "By logging in anonymously, you will not be able to synchronize the data" +
                        " across devices or after uninstalling the app. \nAre you sure you want to proceed?"
            )
        }
    )
}