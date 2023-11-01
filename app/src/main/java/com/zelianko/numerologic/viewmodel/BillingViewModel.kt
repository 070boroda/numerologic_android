package com.zelianko.numerologic.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList


class BillingViewModel(
) : ViewModel() {

    private var _isActiveSub = MutableLiveData(false)
        private set
    val isActiveSub: LiveData<Boolean> = _isActiveSub

    private var _textSub = MutableLiveData<String>()
        private set
    val textSub: LiveData<String> = _textSub

    private var _textPrice = MutableLiveData("")
        private set
    val textPrice: LiveData<String> = _textPrice

    private var _textDiscr = MutableLiveData("")
        private set
    val textDiscr: LiveData<String> = _textDiscr

    private var _offerToken = MutableLiveData("")
        private set
    val offerToken: LiveData<String> = _offerToken
    private var _productDetails = MutableLiveData<ProductDetails>()
        private set
    val productDetails: LiveData<ProductDetails> = _productDetails

    var billingClient: BillingClient? = null


    /**
     * Проверка данных есть подписки или нет
     */
    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if ((billingResult.responseCode == BillingResponseCode.OK) && purchases != null) {
                Log.d("purchases", purchases.size.toString())
                for (purchase in purchases) {
                    Log.d("purchases state ", purchase.purchaseState.toString())
                }

            } else if (billingResult.responseCode == BillingResponseCode.SERVICE_DISCONNECTED) {
                Log.d("BILLING ERROR", "SERVICE_DISCONNECTED")
            }
        }

    /**
     * Инициализация загрузки
     */
    fun initBillingClient(context: Context){
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        Log.d("getCurrentSub", billingClient?.isReady.toString())
        Log.d("getCurrentSub", "initial connection is DONE")
        establishConnection()
    }

    fun establishConnection() {
        Log.d("billingClient", "Start establishConnection")
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    Log.d("billingClient", "Start showProducts")
                    showProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d("billingClient", "Disconect")
                establishConnection()
            }
        })
    }

    /**
     * Получаем список продуктов, доступных для покупки
     */
    fun showProducts() {
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
        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            Log.d("billingClient", "Start query for show product2")
            if (billingResult.responseCode == BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                if (productDetailsList[0].productId.equals("monthly_sub")) {

                    _textSub.postValue(productDetailsList[0].name)

                    _textDiscr.postValue(productDetailsList[0].description)

                    _textPrice.postValue(
                        productDetailsList[0].subscriptionOfferDetails?.get(0)?.pricingPhases?.pricingPhaseList?.get(
                            0
                        )?.formattedPrice
                    )

                    _offerToken.postValue(productDetailsList[0].subscriptionOfferDetails?.get(0)?.offerToken)

                    _productDetails.postValue(productDetailsList[0])
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


    /**
     * По нажатию на кнопку вызывается этот метод для активации подписки
     */
    fun launchPurchaseFlow(
        productDetails: ProductDetails,
        activity: Activity,
        selectedOfferToken: String
    ) {

        val productDetailsParamsList = listOf(
            ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(selectedOfferToken)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        val billingResult = billingClient?.launchBillingFlow(activity, billingFlowParams)
        if (billingResult?.responseCode == BillingResponseCode.OK) {
            _isActiveSub.postValue(true)
            Log.d("testOffer", isActiveSub.value.toString())
        }
    }

    /**
     * Проверка наличия подписки
     *
     * вроде работает
     * нужно сделать еще переподписку
     *
     *https://github.com/wdtheprovider/in-app-purchases-subscription#step-8-check-subscription-this-code-goes-to-your-splash-screen-
     */

    fun checkSubscription(context: Context) {
        billingClient = BillingClient.newBuilder(context).enablePendingPurchases()
            .setListener { billingResult: BillingResult?, list: List<Purchase?>? -> }
            .build()


        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Log.d("testOffer", " onBillingServiceDisconnected")
            }
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    billingClient?.queryPurchasesAsync(
                        QueryPurchasesParams.newBuilder()
                            .setProductType(BillingClient.ProductType.SUBS).build()
                    ) { billingResult1: BillingResult, list: List<Purchase> ->
                        if (billingResult1.responseCode == BillingResponseCode.OK) {
                            Log.d("testOffer", list.size.toString() + " size")
                            if (list.size > 0) {
                                _isActiveSub.postValue(true) // set 1 to activate premium feature
                                var i = 0
                                for (purchase in list) {
                                    //Here you can manage each product, if you have multiple subscription
                                    Log.d(
                                        "testOffer",
                                        purchase.originalJson
                                    ) // Get to see the order information
                                    Log.d("testOffer", " index$i")
                                    i++
                                }
                                Log.d("testOffer", _isActiveSub.value.toString())
                            } else {
                                _isActiveSub.postValue(false) // set 0 to de-activate premium feature
                            }
                        }
                    }
                }
            }
        })
    }

}
