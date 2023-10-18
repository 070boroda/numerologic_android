package com.zelianko.numerologic.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zelianko.numerologic.activiti.GeneralScreen
import com.zelianko.numerologic.activiti.HelpScreen
import com.zelianko.numerologic.activiti.TestScreen

@Composable
fun NagGraph(
    navHostController: NavHostController,
    argument: MutableState<String>
) {
    NavHost(navController = navHostController,
        startDestination = "screen_1",
        modifier = Modifier.background(Color.Black))
    {
        composable("screen_1") {
            GeneralScreen(argument)
        }
        composable("screen_2") {
            HelpScreen()
        }
        composable("screen_3") {
            TestScreen(argument)
        }
    }
}