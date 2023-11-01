package com.zelianko.numerologic

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.mobile.ads.common.MobileAds
import com.zelianko.numerologic.activiti.MainScreen
import com.zelianko.numerologic.viewmodel.BillingViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SelectedDateTextViewModel::class.java]
        val billingViewModel = ViewModelProvider(this)[BillingViewModel::class.java]
        MobileAds.initialize(this) {
            // now you can use ads
        }
        val config = YandexMetricaConfig.newConfigBuilder("25783e36-10b1-4551-bab1-89236908f4af").build()

        YandexMetrica.activate(applicationContext, config)

        YandexMetrica.enableActivityAutoTracking(Application())
        setContent {
            val context = LocalContext.current
            billingViewModel.initBillingClient(context)
            billingViewModel.checkSubscription(context)
            MainScreen(
                viewModel = viewModel,
                billingViewModel = billingViewModel
            )
        }
    }
}
