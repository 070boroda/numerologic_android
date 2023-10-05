package com.zelianko.numerologic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.yandex.mobile.ads.common.MobileAds
import com.zelianko.numerologic.activiti.GeneralScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {
            // now you can use ads
        }


        setContent {
            GeneralScreen()
        }
    }
}
