package com.example.measuremate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.example.measuremate.presentation.dashboard.DashboardScreen
import com.example.measuremate.presentation.details.DetailsScreen
import com.example.measuremate.presentation.navigation.NavGraphSetup
import com.example.measuremate.presentation.signin.SignInScreen
import com.example.measuremate.presentation.theme.MeasureMateTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {                       // Used to specify the content for UI
            MeasureMateTheme {
                val windowSizeClass = calculateWindowSizeClass(activity = this)
                val navController = rememberNavController()
                Scaffold { paddingValues ->
                    NavGraphSetup(
                        navController = navController,
                        windowsSize = windowSizeClass.widthSizeClass,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}