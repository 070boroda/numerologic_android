package com.zelianko.numerologic.bottom_navigation

import androidx.navigation.NavHostController

class NavigationState(
    val navController: NavHostController
) {
    fun navigateTo(route:String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}