package com.zelianko.numerologic.activiti


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.bottom_navigation.ButtonNavigation
import com.zelianko.numerologic.bottom_navigation.NagGraph
import com.zelianko.numerologic.subscriptios.SubscriptionsHelper
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: SelectedDateTextViewModel,
    purchaseDone: Boolean,
    billingPurchaseHelper: SubscriptionsHelper
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            ButtonNavigation(navHostController = navController)
        },
    ) { paddingValues ->
        NagGraph(
            navHostController = navController,
            generalScreenContent = {
                GeneralScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues
                )
            },
            transformationScreen = {
                TransformationScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    purchaseDone = purchaseDone,
                    billingPurchaseHelper = billingPurchaseHelper
                )
            },
            degradationScreen = {
                DegradationScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    purchaseDone = purchaseDone,
                    billingPurchaseHelper = billingPurchaseHelper

                )
            },
            compatibilityScreenContent = { CompatibilityScreen(
                paddingValues = paddingValues,
                purchaseDone = purchaseDone
            ) },
            helpScreenContent = { HelpScreen(paddingValues = paddingValues) }
        )
    }
}