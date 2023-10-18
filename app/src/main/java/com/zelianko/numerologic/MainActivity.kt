package com.zelianko.numerologic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yandex.mobile.ads.common.MobileAds
import com.zelianko.numerologic.activiti.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {
            // now you can use ads
        }
//        Adapty.activate(applicationContext,"public_live_iOVUPvtv.L6IOxy74BdrsXROvwWOF")
        setContent {
            MainScreen()
        }
    }
}
