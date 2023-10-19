package com.zelianko.numerologic.activiti


import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.bottom_navigation.ButtonNavigation
import com.zelianko.numerologic.bottom_navigation.NagGraph
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: SelectedDateTextViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            ButtonNavigation(navController = navController)
        },
    ) {
        NagGraph(
            viewModel = viewModel,
            navHostController = navController
        )
    }
}