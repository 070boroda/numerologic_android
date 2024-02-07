package com.zelianko.numerologic.ads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader


var interstitialAd: InterstitialAd? = null
var interstitialAdLoader: InterstitialAdLoader? = null

@Composable
fun InterstitialAdWrapper(context: Context) {
    AndroidView(factory = { ctx ->
        FrameLayout(ctx).apply {
            interstitialAdLoader = InterstitialAdLoader(context).apply {
                setAdLoadListener(object : InterstitialAdLoadListener {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                        // The ad was loaded successfully. Now you can show loaded ad.
                    }

                    override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                        // Ad failed to load with AdRequestError.
                        // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                    }
                })
            }
            loadInterstitialAd()
            showAd(context = context)
        }
    }
    )
}

private fun loadInterstitialAd() {
    val adRequestConfiguration = AdRequestConfiguration.Builder("R-M-3095140-5").build()
    interstitialAdLoader?.loadAd(adRequestConfiguration)
}

private fun showAd(context: Context) {
    interstitialAd?.apply {
        setAdEventListener(object : InterstitialAdEventListener {
            override fun onAdShown() {
                // Called when ad is shown.
            }

            override fun onAdFailedToShow(adError: AdError) {
                // Called when an InterstitialAd failed to show.
                // Clean resources after Ad dismissed
                interstitialAd?.setAdEventListener(null)
                interstitialAd = null

                // Now you can preload the next interstitial ad.
                loadInterstitialAd()
            }

            override fun onAdDismissed() {
                // Called when ad is dismissed.
                // Clean resources after Ad dismissed
                interstitialAd?.setAdEventListener(null)
                interstitialAd = null

                // Now you can preload the next interstitial ad.
                loadInterstitialAd()
            }

            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
            }

            override fun onAdImpression(impressionData: ImpressionData?) {
                // Called when an impression is recorded for an ad.
            }
        })
        context.findActivity()?.let { show(it) }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}