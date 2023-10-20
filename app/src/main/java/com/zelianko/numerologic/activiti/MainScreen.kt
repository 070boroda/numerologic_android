package com.zelianko.numerologic.activiti


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.bottom_navigation.ButtonNavigation
import com.zelianko.numerologic.bottom_navigation.NagGraph
import com.zelianko.numerologic.viewmodel.QonversionViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: SelectedDateTextViewModel,
    qViewModel: QonversionViewModel
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
            compatibilityScreenContent = { CompatibilityScreen(paddingValues = paddingValues)},
            helpScreenContent = { HelpScreen(paddingValues = paddingValues)},
            testScreenContent = { TestScreen(
                paddingValues = paddingValues,
                viewModel = qViewModel) }
        )
    }
}