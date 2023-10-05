package com.zelianko.numerologic.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest

@Composable
fun Banner(id: Int) {
    AndroidView(factory = { context ->
        BannerAdView(context).apply {
            setAdUnitId(context.getString(id))
            setAdSize(BannerAdSize.stickySize(context, 300))
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
        }
    })
}