package com.example.measuremate.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.measuremate.presentation.add_item.AddItemScreen
import com.example.measuremate.presentation.dashboard.DashboardScreen
import com.example.measuremate.presentation.details.DetailsScreen
import com.example.measuremate.presentation.signin.SignInScreen
import com.example.measuremate.presentation.signin.SignInViewModel

@Composable
fun NavGraphSetup(
    navController: NavHostController,
    windowsSize: WindowWidthSizeClass,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {
        composable<Route.SignInScreen> {
            val signInViewModel: SignInViewModel = hiltViewModel()
            val state by signInViewModel.state.collectAsStateWithLifecycle()
            SignInScreen(
                windowsSize,
                paddingValues = paddingValues,
                state = state,
                onEvent = signInViewModel::onEvent
            )
        }

        composable<Route.DashboardScreen> {
            DashboardScreen(
                paddingValues = paddingValues,
                onFabClicked = {navController.navigate(Route.AddItemScreen)},
                onItemCardClicked = { bodyPartId ->
                    navController.navigate(Route.DetailsScreen(bodyPartId))}
            )
        }

        composable<Route.AddItemScreen>(
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(durationMillis = 500),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            } ,
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(durationMillis = 500),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            AddItemScreen(
                paddingValues = paddingValues,
                onBackIconClick = {navController.navigateUp()} // navigateUp takes the screen back to the previous
            )                                                  // one, from which we came earlier.
        }

        composable<Route.DetailsScreen>(
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(durationMillis = 500),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            } ,
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(durationMillis = 500),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { navBackStackEntry ->
            val bodyPartId = navBackStackEntry.toRoute<Route.DetailsScreen>().bodyPartId
            DetailsScreen(
                paddingValues = paddingValues,
                bodyPartId = bodyPartId,
                onBackIconClick = {navController.navigateUp()}
            )
        }
    }
}