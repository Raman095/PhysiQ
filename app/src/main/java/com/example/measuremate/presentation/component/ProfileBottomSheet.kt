package com.example.measuremate.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState ,
    isOpen: Boolean,
    buttonLoadingState: Boolean,
    buttonPrimaryText: String,
    onGoogleButtonClick: () -> Unit,
    onBottomSheetDismiss: () -> Unit
) {
    if(isOpen) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = { onBottomSheetDismiss() },
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier= Modifier.height(10.dp))
                    HorizontalDivider()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicPlaceHolder(
                    placeHolderSize = 120.dp,
                    borderWidth = 2.dp,
                    profilePictureUrl = null,
                    padding = 5.dp
                )
                Spacer(modifier= Modifier.height(10.dp))
                Text(
                    text = "Anonymous",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier= Modifier.height(4.dp))
                Text(
                    text = "anonymous@gmail.com",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier= Modifier.height(20.dp))
                GoogleSignInButton(
                    onClick = { onGoogleButtonClick() },
                    primaryText = buttonPrimaryText,
                    loadingState = buttonLoadingState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProfileBottomSheetPreview() {
    ProfileBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        isOpen = true,
        onBottomSheetDismiss = { },
        onGoogleButtonClick = { },
        buttonPrimaryText = "Sign out",
        buttonLoadingState = false
    )
}