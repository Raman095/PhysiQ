package com.example.measuremate.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.measuremate.R
import com.example.measuremate.presentation.component.AnonymousSignInButton
import com.example.measuremate.presentation.component.GoogleSignInButton
import com.example.measuremate.presentation.component.PhysiQDialog

@Composable
fun SignInScreen(
    windowSize: WindowWidthSizeClass
) {

    var isSignInAnonymousDialogOpen by rememberSaveable { mutableStateOf(false) }

    PhysiQDialog (
        isOpen = isSignInAnonymousDialogOpen,
        title = "Login anonymously?",
        onDialogDismiss = { isSignInAnonymousDialogOpen = false },
        onConfirmButtonClick = { isSignInAnonymousDialogOpen = false },
        body = {
            Text(
                text = "By logging in anonymously, you will not be able to synchronize the data" +
                        " across devices or after uninstalling the app. \nAre you sure you want to proceed?"
            )
        }
    )

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.sign_in_logo),
                    contentDescription = null,
                    modifier = Modifier.size(145.dp)
                        .clip(shape = CircleShape)
                )
                Spacer(modifier = Modifier.height(220.dp))
                GoogleSignInButton (
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(10.dp))
                AnonymousSignInButton (
                    onClick = { isSignInAnonymousDialogOpen = true }
                )
            }
    }
        else -> {
            Row {
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.sign_in_logo),
                        contentDescription = null,
                        modifier = Modifier.size(145.dp)
                            .clip(shape = CircleShape)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    GoogleSignInButton (
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AnonymousSignInButton (
                        onClick = { isSignInAnonymousDialogOpen = true }
                    )
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        windowSize = WindowWidthSizeClass.Medium
    )
}