package com.zelianko.numerologic

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.android.billingclient.api.BillingClient
import com.yandex.mobile.ads.common.MobileAds
import com.zelianko.numerologic.activiti.MainScreen
import com.zelianko.numerologic.viewmodel.BillingViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SelectedDateTextViewModel::class.java]
        val billingViewModel = ViewModelProvider(this)[BillingViewModel::class.java]
        MobileAds.initialize(this) {
            // now you can use ads
        }

        setContent {
            val context = LocalContext.current
            billingViewModel.initBillingClient(context)
            billingViewModel.checkSubscription(context)
            MainScreen(
                viewModel = viewModel,
                billViewModel = billingViewModel
            )
        }
    }
}
