package com.zelianko.numerologic.viewmodel

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.SkuDetailsResponseListener
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList

class BillingClientWrapper(context: Context) : PurchasesUpdatedListener {

    interface OnQueryProductsListener {
        fun onSuccess(products: List<ProductDetails>)
        fun onFailure(error: Error)
    }

    class Error(val responseCode: Int, val debugMessage: String)

    private val billingClient = BillingClient
        .newBuilder(context)
        .enablePendingPurchases()
        .setListener(this)
        .build()

    fun queryProducts(listener: OnQueryProductsListener) {

        Log.d("billingClient", "Start query for show product")
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    ImmutableList.of(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("monthly_sub")
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    )
                )
                .build()

        Log.d("billingClient", "Start query for show product3")
        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                             productDetailsList ->
            Log.d("billingClient", "Start query for show product2")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                if (productDetailsList[0].productId.equals("monthly_sub")) {
                    //_textSub.value = productDetailsList[0].name
                    Log.d("Product details id", productDetailsList[0].productId)
                    Log.d("Product details name", productDetailsList[0].name)
                    Log.d("Product details product type", productDetailsList[0].productType)
                    Log.d(
                        "Product details product price",
                        productDetailsList[0].oneTimePurchaseOfferDetails?.priceCurrencyCode.toString()
                    )
                    Log.d(
                        "Product details subscriptionOfferDetails",
                        productDetailsList[0].subscriptionOfferDetails.toString()
                    )
                }
            } else {
                Log.d(
                    "billingClient",
                    billingResult.responseCode.toString() + " " + billingResult.debugMessage
                )
                Log.d("billingClient Product size", productDetailsList.size.toString())
                Log.d("billingClient", "Product is empty")
            }
        }
    }

    private fun queryProductsForType(
        skusList: List<String>,
        @BillingClient.SkuType type: String,
        listener: SkuDetailsResponseListener
    ) {
        onConnected {
            billingClient.querySkuDetailsAsync(
                SkuDetailsParams.newBuilder().setSkusList(skusList).setType(type).build(),
                listener
            )
        }
    }

    private fun onConnected(block: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                block()
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchaseList: MutableList<Purchase>?
    ) {
        // here come callbacks about new purchases
    }
}