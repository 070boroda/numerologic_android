package com.zelianko.numerologic

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.zelianko.kitchencalculator.constants.StringConstants
import com.zelianko.numerologic.activiti.MainScreen
import com.zelianko.numerologic.ads.AppOpenAdManager
import com.zelianko.numerologic.viewmodel.BillingViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SelectedDateTextViewModel::class.java]
        val billingViewModel = ViewModelProvider(this)[BillingViewModel::class.java]
        com.yandex.mobile.ads.common.MobileAds.initialize(this) {}

        com.yandex.mobile.ads.common.MobileAds.enableDebugErrorIndicator(false)
        com.google.android.gms.ads.MobileAds.initialize(this) {}
//        val config =
//            YandexMetricaConfig.newConfigBuilder("25783e36-10b1-4551-bab1-89236908f4af").build()
//        YandexMetrica.activate(applicationContext, config)
//        YandexMetrica.enableActivityAutoTracking(Application())

        val config =
            AppMetricaConfig.newConfigBuilder("25783e36-10b1-4551-bab1-89236908f4af").build()
        // Initializing the AppMetrica SDK.
        AppMetrica.activate(this, config)

        setContent {
            val context = LocalContext.current
            billingViewModel.initBillingClient(context)
            billingViewModel.checkSubscription(context)

            val isActiveSub = billingViewModel.isActiveSub.observeAsState()

            if (isActiveSub.value == false) {
                AppOpenAdManager(this.application, StringConstants.StartAdAppScreenId)
            }

//            val app = context.applicationContext as Application
//            AppOpenAdManager(findActivity(), "ca-app-pub-9309082672837567/9615807629")
            // AppOpenAdManager(app, "ca-app-pub-3940256099942544/3419835294") //test id
            MainScreen(
                viewModel = viewModel,
                billingViewModel = billingViewModel
            )
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


