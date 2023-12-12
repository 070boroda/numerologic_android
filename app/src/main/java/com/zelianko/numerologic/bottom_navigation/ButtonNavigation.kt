package com.zelianko.numerologic.bottom_navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.zelianko.numerologic.ui.theme.NavBarColor
import com.zelianko.numerologic.viewmodel.BillingViewModel

@Composable
fun ButtonNavigation(
    navHostController: NavHostController,
    billingViewModel: BillingViewModel

) {
    val listItem = listOf(
        BottomItem.Screen1,
        BottomItem.Screen4,
        BottomItem.Screen5,
        BottomItem.Screen3,
        BottomItem.Screen2,
    )
    val isActiveSub = billingViewModel.isActiveSub.observeAsState()
    NavigationBar(
        modifier = Modifier.fillMaxHeight(0.08f),
        containerColor = NavBarColor,
    ) {

        val navigationState = remember {
            NavigationState(navHostController)
        }
        val backStateEntry by navHostController.currentBackStackEntryAsState()
        val currentRow = backStateEntry?.destination?.route
        val context = LocalContext.current
        listItem.forEach { bottomItem ->
            BottomNavigationItem(
                selected = currentRow == bottomItem.route,

                onClick = {
//                    if (isActiveSub.value != true) {
//                        showInterstialAd(context)
//                    }
                    navigationState.navigateTo(bottomItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomItem.iconId),
                        contentDescription = "Icon",
                    )
                },
                label = {
                    Text(
                        text = bottomItem.title,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                },
                selectedContentColor = Color.Gray,
                unselectedContentColor = Color.White
            )
        }

    }
}

private fun showInterstialAd(context: Context) {
    InterstitialAd.load(
        context,
        "ca-app-pub-9309082672837567/9179291958", //Change this with your own AdUnitID!
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ADS", "ERROR load")
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitialAd.show(context.findActivity())
                Log.d("ADS", "load DONE")
            }
        }
    )
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}