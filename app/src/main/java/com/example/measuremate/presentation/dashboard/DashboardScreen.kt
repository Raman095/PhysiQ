package com.example.measuremate.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.measuremate.domain.model.BodyPart
import com.example.measuremate.domain.model.User
import com.example.measuremate.domain.model.predefinedBodyParts
import com.example.measuremate.presentation.component.PhysiQDialog
import com.example.measuremate.presentation.component.ProfileBottomSheet
import com.example.measuremate.presentation.component.ProfilePicPlaceHolder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    paddingValues: PaddingValues,
    onFabClicked: () -> Unit,
    onItemCardClicked: (String) -> Unit
) {

    var isProfileBottomSheetOpen by remember { mutableStateOf(false) }

    val user = User(
        name = "Raman",
        email = "james.madison@examplepetstore.com",
        profilePictureUrl = null,
        isAnonymous = false
    )

    var isSignOutDialogOpen by rememberSaveable { mutableStateOf(false) }

    PhysiQDialog (
        isOpen = isSignOutDialogOpen,
        title = "Sign Out?",
        onDialogDismiss = { isSignOutDialogOpen = false },
        onConfirmButtonClick = { isSignOutDialogOpen = false },
        body = {
            Text(
                text = "Are you sure, you want to sign out?"
            )
        }
    )
    
    ProfileBottomSheet(
        isOpen = isProfileBottomSheetOpen,
        user = user,
        buttonLoadingState = false,
        buttonPrimaryText = "Sign out",
        onBottomSheetDismiss = { isProfileBottomSheetOpen = false },
        onGoogleButtonClick = { isSignOutDialogOpen = true }
    )

    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DashboardTopBar(
                profilePictureUrl = user.profilePictureUrl,
                onProfilePicClick = { isProfileBottomSheetOpen = true }
            )
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(16.dp, top = 8.dp, 16.dp, 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(predefinedBodyParts) { item ->
                    ItemCard(
                        bodyPart = item,
                        onItemCardClicked = onItemCardClicked
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(24.dp),
            onClick = { onFabClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Button"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier,
    profilePictureUrl: String?,
    onProfilePicClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0,0,0,0),
        title = {
            Text( text = "PhysiQ")
        },
        actions = {        // Used to place the element on the right side of Top Bar

            // When user taps the profile icon -> calls onProfilePicClick()
            // That lambda changes (look up in the code) isProfileBottomSheetOpen = true -> opens bottom sheet
            IconButton( onClick = { onProfilePicClick() }) {
                ProfilePicPlaceHolder(
                    placeHolderSize = 45.dp,
                    borderWidth = 1.dp,
                    profilePictureUrl = profilePictureUrl,
                    padding = 2.dp
                )
            }
        }
    )
}

@Composable
private fun ItemCard(
    modifier: Modifier = Modifier,
    bodyPart: BodyPart,
    onItemCardClicked: (String) -> Unit
) {
    Card(
        modifier = modifier,
        onClick = { bodyPart.bodyPartId?.let { onItemCardClicked(it) } }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(8f),
                text = bodyPart.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${bodyPart.latestValue ?: ""} ${bodyPart.measuringUnit}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier= Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Show Details",
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(
        onItemCardClicked = {},
        onFabClicked = {},
        paddingValues = PaddingValues(0.dp)
    )
}