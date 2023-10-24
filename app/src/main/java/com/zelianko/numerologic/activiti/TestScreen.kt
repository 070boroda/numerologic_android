package com.zelianko.numerologic.activiti

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.zelianko.numerologic.viewmodel.BillingViewModel

@Composable
fun TestScreen(
    paddingValues: PaddingValues,
    viewModel: BillingViewModel
) {
    val textSub = viewModel.textSub.observeAsState("")
    val textDiscr = viewModel.textDiscr.observeAsState("")
    val textPrice = viewModel.textPrice.observeAsState("")
    val tokenOffer = viewModel.offerToken.observeAsState("")
    val productDetails = viewModel.productDetails.observeAsState()
    val activity = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                productDetails.value?.let {
                    viewModel.launchPurchaseFlow(
                        it,
                        activity,
                        tokenOffer.value
                    )
                }
            },
            colors = ButtonDefaults.textButtonColors(
                Color.Red
            )
        ) {
            Text(text = "Можете оформить ${textSub.value}  ${textDiscr.value}  ${textPrice.value}")
        }
    }
}