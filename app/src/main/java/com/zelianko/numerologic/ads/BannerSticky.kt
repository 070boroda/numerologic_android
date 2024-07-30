package com.zelianko.numerologic.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import kotlin.math.roundToInt

@Composable
fun BannerSticky(id: Int) {
    AndroidView(factory = { context ->

        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val bounds = windowMetrics.bounds

        var adWithPixels = context.resources.displayMetrics.widthPixels.toFloat()

        if (adWithPixels == 0f) {
            adWithPixels = bounds.width().toFloat()
        }

        val density = context.resources.displayMetrics.density
        val adWith = (adWithPixels / density).toInt()


        BannerAdView(context).apply {
            setAdUnitId(context.getString(id))
            setAdSize(BannerAdSize.stickySize(context, adWith))
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
        }
    })
}

//demo-banner-yandex
@Composable
fun BannerInline(id: String, divider: Int) {
    AndroidView(factory = { context ->

        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val bounds = windowMetrics.bounds

        var adWithPixels = context.resources.displayMetrics.widthPixels.toFloat()

        if (adWithPixels == 0f) {
            adWithPixels = bounds.width().toFloat()
        }

        val density = context.resources.displayMetrics.density
        val adWith = (adWithPixels / density).toInt()

        val screenHeight =
            context.resources.displayMetrics.run { heightPixels / density }.roundToInt()
        val maxAdHeight = screenHeight / divider


        BannerAdView(context).apply {
            setAdUnitId(id)
            setAdSize(BannerAdSize.inlineSize(context, adWith, maxAdHeight))
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
        }
    })
}