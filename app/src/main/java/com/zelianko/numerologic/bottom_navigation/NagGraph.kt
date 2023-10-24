package com.zelianko.numerologic.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@Composable
fun NagGraph(
    navHostController: NavHostController,
    generalScreenContent: @Composable () -> Unit,
    compatibilityScreenContent: @Composable () -> Unit,
    helpScreenContent: @Composable () -> Unit,
    testScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = "screen_1",
        modifier = Modifier.background(Color.Black)
    ) {
        composable("screen_1") {
            generalScreenContent()
//            GeneralScreen(
//                viewModel = viewModel
//            )
        }
        composable("screen_3") {
            compatibilityScreenContent()

        }
        composable("screen_2") {
            helpScreenContent()

        }
        composable("screen_4") {
            testScreenContent()

        }
    }
}