package com.zelianko.numerologic.activiti

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.zelianko.numerologic.viewmodel.BillingViewModel

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    billingViewModel: BillingViewModel
) {
    val textSub = billingViewModel.textSub.observeAsState("")
    val textDiscr = billingViewModel.textDiscr.observeAsState("")
    val textPrice = billingViewModel.textPrice.observeAsState("")
    val tokenOffer = billingViewModel.offerToken.observeAsState("")
    val productDetails = billingViewModel.productDetails.observeAsState()
    val activity = LocalContext.current as Activity

    AlertDialog(
        containerColor = Color.White.copy(red = 0.143f, green = 0.187f, blue = 0.241f),
        iconContentColor = Color.White,
        titleContentColor = Color.White,
        textContentColor = Color.White,
        icon = {
            Icon(Icons.Default.Info, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "Оформить подписку")
        },
        text = {
            Text(
                text = textSub.value + " " + textPrice.value + "/мес. " + textDiscr.value + " "
                        + "Вы всегда можете отменить подписку в личном кабинете Play Market")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    productDetails.value?.let {
                        billingViewModel.launchPurchaseFlow(
                            it,
                            activity,
                            tokenOffer.value
                        )
                    }
                }
            ) {
                Text("Подписаться")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Отмена")
            }
        }
    )
}