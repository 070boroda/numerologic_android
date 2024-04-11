package com.zelianko.numerologic.ads

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.*


interface OnShowAdCompleteListener {
    fun onShowAdComplete()
}

class AppOpenAdManager : Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null
    private val adUnit: String

    private val lifecycleEventObserver = LifecycleEventObserver { source, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            Log.e("G Start App Ads", "ON_RESUME: showOpen Ad")
            currentActivity?.let { showAdIfAvailable(it) }
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            Log.e("G Start App Ads", "paused")
        }
    }

    constructor(application: Application, adUnit: String) {
        this.adUnit = adUnit
        Log.e("G Start App Ad", "Ad Open ID : $adUnit" )
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
        // SDK or another activity class implemented by a third party mediation partner. Updating the
        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
        // one that shows the ad.
        if (!isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}


    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
    private var loadTime: Long = 0

    fun loadAd(context: Context) {
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable()) {
            return
        }

        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            adUnit,
            request,
            object : AppOpenAdLoadCallback() {

                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                    Log.d("G Start App Ads", "onAdLoaded.")
                    //Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show()
                }


                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.d("G Start App Ads", "onAdFailedToLoad: " + loadAdError.message)
                    //Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    /** Check if ad was loaded more than n hours ago. */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    /** Check if ad exists and can be shown. */
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(1)
    }

    private fun showAdIfAvailable(activity: Activity) {
        showAdIfAvailable(
            activity,
            object : OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    // Empty because the user will go back to the activity that shows the ad.
                }
            }
        )
    }

    fun showAdIfAvailable(activity: Activity, onShowAdCompleteListener: OnShowAdCompleteListener) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d("G Start App Ads", "The app open ad is already showing.")
            return
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            Log.d("G Start App Ads", "The app open ad is not ready yet.")
            onShowAdCompleteListener.onShowAdComplete()
            loadAd(activity)
            return
        }

        Log.d("G Start App Ads", "Will show ad.")

        appOpenAd!!.setFullScreenContentCallback(
            object : FullScreenContentCallback() {
                /** Called when full screen content is dismissed. */
                override fun onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    appOpenAd = null
                    isShowingAd = false
                    Log.d("G Start App Ads", "onAdDismissedFullScreenContent.")
                    /* Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT)
                         .show()*/

                    onShowAdCompleteListener.onShowAdComplete()
                    loadAd(activity)
                }

                /** Called when fullscreen content failed to show. */
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    appOpenAd = null
                    isShowingAd = false
                    Log.d("G Start App Ads", "onAdFailedToShowFullScreenContent: " + adError.message)
                    /*Toast.makeText(
                        activity,
                        "onAdFailedToShowFullScreenContent",
                        Toast.LENGTH_SHORT
                    ).show()*/

                    onShowAdCompleteListener.onShowAdComplete()
                    loadAd(activity)
                }

                /** Called when fullscreen content is shown. */
                override fun onAdShowedFullScreenContent() {
                    Log.d("G Start App Ads", "onAdShowedFullScreenContent.")
                    /*Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
                        .show()*/
                }
            }
        )
        isShowingAd = true
        appOpenAd!!.show(activity)
    }
}