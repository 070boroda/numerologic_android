package com.zelianko.numerologic.ads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdmobBanner(modifier: Modifier = Modifier,
                textId: String = "ca-app-pub-3940256099942544/9214589741"
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val windowMetrics: WindowMetrics =
                WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
            val bounds = windowMetrics.bounds

            var adWithPixels = context.resources.displayMetrics.widthPixels.toFloat()

            if (adWithPixels == 0f) {
                adWithPixels = bounds.width().toFloat()
            }

            val density = context.resources.displayMetrics.density
            val adWith = (adWithPixels / density).toInt()

            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = textId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}