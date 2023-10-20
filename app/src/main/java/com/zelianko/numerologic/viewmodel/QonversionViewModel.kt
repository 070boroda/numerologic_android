package com.zelianko.numerologic.viewmodel

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.dto.QonversionError
import com.qonversion.android.sdk.dto.entitlements.QEntitlement
import com.qonversion.android.sdk.dto.offerings.QOffering
import com.qonversion.android.sdk.dto.offerings.QOfferings
import com.qonversion.android.sdk.dto.products.QProduct
import com.qonversion.android.sdk.listeners.QonversionEntitlementsCallback
import com.qonversion.android.sdk.listeners.QonversionOfferingsCallback

class QonversionViewModel : ViewModel() {

    var offerings by mutableStateOf<List<QOffering>>(emptyList())
        private set

    var hasPremiumPermission by mutableStateOf(false)
        private set

    init {
        Log.d(TAG, "Initial inin method")
        loadOfferings()
        checkSub()
    }

    /**
     * Получаем от Гугла список подписак
     */
    private fun loadOfferings() {
        Qonversion.shared.offerings(object : QonversionOfferingsCallback {
            override fun onSuccess(offerings: QOfferings) {
                val offering = offerings.offeringForID("monthly_sub")
                if (offering != null) {
                    this@QonversionViewModel.offerings = offerings.availableOfferings
                }
            }

            override fun onError(error: QonversionError) {
                Log.d(TAG, "onError: ${error.description}")
            }
        })
    }

    /**
     * Получаем подписку по айди из доступных подписок
     */
    private fun loadOfferingById(id: String) {
        Qonversion.shared.offerings(object : QonversionOfferingsCallback {
            override fun onSuccess(offerings: QOfferings) {
                val offering = offerings.offeringForID(id)
                if (offering != null) {
                    this@QonversionViewModel.offerings = listOf(offering)
                }
            }

            override fun onError(error: QonversionError) {
                Log.d(TAG, "onError: ${error.description}")
            }
        })
    }

    /**
     * Проверяем если активная подписка
     */
    fun checkSub() {
        Qonversion.shared.checkEntitlements(object : QonversionEntitlementsCallback {
            override fun onSuccess(entitlements: Map<String, QEntitlement>) {
                val premiumEntitlement = entitlements["monthly_sub"]
                if (premiumEntitlement != null && premiumEntitlement.isActive) {
                    hasPremiumPermission = true
                }
            }

            override fun onError(error: QonversionError) {
                Log.d(TAG, "onError: ${error.description}")
            }
        })
    }

    fun purchase(product: QProduct, context: Context) {
        Qonversion.shared.purchase(findActivity(context), product, callback = object :
            QonversionEntitlementsCallback {
            override fun onSuccess(entitlements: Map<String, QEntitlement>) {
                Toast.makeText(context, "Purchase succeeded", Toast.LENGTH_LONG).show()
            }
            override fun onError(error: QonversionError) {
                showError(context, error, TAG)
            }
        })
    }

    fun showError(context: Context, error: QonversionError, logTag: String) {
        val code = error.code                           // Error enum code
        val description = error.description             // Error enum code description
        val additionalMessage =
            error.additionalMessage // Additional error information (if possible)
        Toast.makeText(context, error.description, Toast.LENGTH_LONG).show()
        Log.e(
            logTag,
            "error code: $code, description: $description, additionalMessage: $additionalMessage"
        )
    }



    /**
     * Поиск активити
     */
    fun findActivity(context: Context): Activity {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("Permissions should be called in the context of an Activity")
    }
}