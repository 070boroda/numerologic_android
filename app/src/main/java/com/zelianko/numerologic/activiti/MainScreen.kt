package com.zelianko.numerologic.activiti


import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.bottom_navigation.ButtonNavigation
import com.zelianko.numerologic.bottom_navigation.NagGraph

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val argument = remember { mutableStateOf("")
    }
    Scaffold(
        bottomBar = {
            ButtonNavigation(navController = navController)
        },
    ) {
        NagGraph(
            navHostController = navController,
            argument = argument)
    }
}