package com.zelianko.numerologic

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.zelianko.numerologic.activiti.MainScreen
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
        com.yandex.mobile.ads.common.MobileAds.initialize(this) {
            // now you can use ads
        }

        com.yandex.mobile.ads.common.MobileAds.enableDebugErrorIndicator(false)
//        val config =
//            YandexMetricaConfig.newConfigBuilder("25783e36-10b1-4551-bab1-89236908f4af").build()
//
//        YandexMetrica.activate(applicationContext, config)
//
//        YandexMetrica.enableActivityAutoTracking(Application())
        val config =
            AppMetricaConfig.newConfigBuilder("25783e36-10b1-4551-bab1-89236908f4af").build()
        // Initializing the AppMetrica SDK.
        AppMetrica.activate(this, config)

        com.google.android.gms.ads.MobileAds.initialize(this) {}

        setContent {
            val context = LocalContext.current
            billingViewModel.initBillingClient(context)
            billingViewModel.checkSubscription(context)
//
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


