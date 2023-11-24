package com.zelianko.numerologic.activiti


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.bottom_navigation.ButtonNavigation
import com.zelianko.numerologic.bottom_navigation.NagGraph
import com.zelianko.numerologic.viewmodel.BillingViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: SelectedDateTextViewModel,
    billingViewModel: BillingViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            ButtonNavigation(navHostController = navController, billingViewModel = billingViewModel)
        },
    ) { paddingValues ->
        NagGraph(
            navHostController = navController,
            generalScreenContent = {
                GeneralScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    billingViewModel = billingViewModel
                )
            },
            transformationScreen = {
                TransformationScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    billingViewModel = billingViewModel
                )
            },
            degradationScreen = {
                DakScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    billingViewModel = billingViewModel
                )
            },
            compatibilityScreenContent = {
                CompatibilityScreen(
                    paddingValues = paddingValues,
                    billingViewModel = billingViewModel,
                )
            },
            helpScreenContent = {
                HelpScreen(
                    paddingValues = paddingValues,
                    billingViewModel = billingViewModel
                )
            }
        )
    }
}