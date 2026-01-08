package com.example.measuremate.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable   // helps in passing data between screens
sealed class Route {
    @Serializable
    data object SignInScreen: Route()
    @Serializable
    data object DashboardScreen: Route()
    @Serializable
    data object AddItemScreen: Route()
    @Serializable
    data class DetailsScreen(val bodyPartId: String): Route()

    // In some screen, we will need data from another screen, for example here, when we will go from
    // dashboard screen to details screen, we will need "bodyPartId" from dashboard screen to show in
    // DetailsScreen, for that we defined DetailsScreen as "data class", that way we can use the data
    // from a screen in different screen.
}