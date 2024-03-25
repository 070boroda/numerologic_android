package com.zelianko.numerologic.activiti

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelianko.numerologic.R
import com.zelianko.numerologic.viewmodel.BillingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
//@Preview(showBackground = true)
fun SubPurScreen(
    paddingValues: PaddingValues,
    billingViewModel: BillingViewModel,
    onDismissRequest: () -> Unit,
) {

//    val textSub = billingViewModel.textSub.observeAsState("")
    val textPrice = billingViewModel.textPrice.observeAsState("")
    val tokenOffer = billingViewModel.offerToken.observeAsState("")
    val productDetails = billingViewModel.productDetails.observeAsState()
    val activity = LocalContext.current as Activity
    Scaffold(
        modifier = Modifier.padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.gradient_top),
                            colorResource(R.color.gradient_bottom)
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDismissRequest() }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Закрыть экран")
                }
            }

            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Психоматрица",
                modifier = Modifier.padding(top = 2.dp),
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )

            Text(
                text = "квадрат Пифагора",
                modifier = Modifier.padding(top = 2.dp),
                style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.White
                ),
                onClick = {
                    productDetails.value?.let {
                        billingViewModel.launchPurchaseFlow(
                            it,
                            activity,
                            tokenOffer.value
                        )
                    }
                }) {
                Text("Подписаться " + textPrice.value + "/мес.", fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Оформив подписку, вы:",
                modifier = Modifier.padding(top = 2.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
//            Text(
//                text = "1. Получаете доступ к расчету",
//                modifier = Modifier.padding(top = 5.dp),
//                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
//                color = Color.DarkGray
//            )
//            Text(
//                text = "совместимости",
//                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
//                color = Color.DarkGray
//            )

            Text(
                text = "1. Получаете доступ к расчету",
                modifier = Modifier.padding(top = 5.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
            Text(
                text = " Трансформации и Дегродации",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
            Text(
                text = "2. Расчет ДИССОНАНСОВ",
                modifier = Modifier.padding(all = 8.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )

            Text(
                text = "3. Скрываете рекламу",
                modifier = Modifier.padding(all = 8.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )

            Text(
                text = "Вы всегда можете отменить подписку",
                modifier = Modifier.padding(all = 8.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
            Text(
                text = "в личном кабинете",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
            Text(
                text = "Google Market",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.size(80.dp))
        }
    }
}
