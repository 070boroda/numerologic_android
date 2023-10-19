package com.zelianko.numerologic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.yandex.mobile.ads.common.MobileAds
import com.zelianko.numerologic.activiti.MainScreen
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SelectedDateTextViewModel::class.java]
        MobileAds.initialize(this) {
            // now you can use ads
        }
        setContent {
            MainScreen(
                viewModel = viewModel
            )
        }
    }
}
