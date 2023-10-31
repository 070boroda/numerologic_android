package com.zelianko.numerologic.activiti


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.bottom_navigation.ButtonNavigation
import com.zelianko.numerologic.bottom_navigation.NagGraph
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: SelectedDateTextViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            ButtonNavigation(navHostController = navController)
        },
    ) {paddingValues ->
        NagGraph(
            navHostController = navController,
            generalScreenContent = {GeneralScreen(viewModel = viewModel, paddingValues = paddingValues)},
            transformationScreen = { TransformationScreen(viewModel = viewModel, paddingValues = paddingValues)},
            compatibilityScreenContent = { CompatibilityScreen(paddingValues = paddingValues)},
            helpScreenContent = { HelpScreen(paddingValues = paddingValues)}
        )
    }
}