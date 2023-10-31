package com.zelianko.numerologic.activiti

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelianko.numerologic.subscriptios.SubscriptionsHelper

@Composable
fun ButtonInit(
    billingPurchaseHelper: SubscriptionsHelper,

) {
    Button(
        onClick = {
            billingPurchaseHelper.initializePurchase()
        },

        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        modifier = Modifier.wrapContentSize(),
        // enabled = purchaseDone
    ) {
        Icon(
            Icons.Filled.Check,
            contentDescription = "Оформить подписку",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            "Купить подписку",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}