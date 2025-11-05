package com.example.measuremate.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.measuremate.R
import com.example.measuremate.presentation.theme.customBlue
import com.example.measuremate.presentation.theme.customPink


@Composable
fun ProfilePicPlaceHolder(
    modifier: Modifier = Modifier,
    placeHolderSize: Dp,
    borderWidth: Dp,
    profilePictureUrl: String?,
    padding: Dp
) {
    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(profilePictureUrl)
        .crossfade(true)
        .build()

    Box(modifier = modifier
        .size(placeHolderSize)
        .border(
            brush = Brush.linearGradient(listOf(customBlue, customPink)),
            width = borderWidth,
            shape = CircleShape
        )
        .padding(padding)   // Gave space between border and pic, your choice to remove it or not
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = imageRequest,
            contentDescription = "Profile Pic",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.sign_in_logo),
            error = painterResource(R.drawable.sign_in_logo)
        )
    }
}

@Preview
@Composable
fun ProfilePicPlaceHolderPreview() {
    ProfilePicPlaceHolder(
        placeHolderSize = 120.dp,
        borderWidth = 3.dp,
        profilePictureUrl = null,
        padding = 5.dp
    )
}