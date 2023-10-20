package com.zelianko.numerologic

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.QonversionConfig
import com.qonversion.android.sdk.dto.QLaunchMode
import com.yandex.mobile.ads.common.MobileAds
import com.zelianko.numerologic.activiti.MainScreen
import com.zelianko.numerologic.viewmodel.QonversionViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SelectedDateTextViewModel::class.java]

        MobileAds.initialize(this) {
            // now you can use ads
        }
        val qonversionConfig = QonversionConfig.Builder(
            this,
            "UMxDfCsgLGRFJ7A-wYHHb0r5SesaGsyd",
            QLaunchMode.SubscriptionManagement
        ).build()
        Log.d(ContentValues.TAG, "Initial init Activiti")
        Qonversion.initialize(qonversionConfig)
        val qviewModel = ViewModelProvider(this)[QonversionViewModel::class.java]
        setContent {
            MainScreen(
                viewModel = viewModel,
                qViewModel = qviewModel
            )
        }
    }
}
