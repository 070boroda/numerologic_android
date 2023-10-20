package com.zelianko.numerologic.activiti

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zelianko.numerologic.viewmodel.QonversionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    viewModel: QonversionViewModel,
    paddingValues: PaddingValues,
    context: Context = LocalContext.current,
) {

    Surface(
        modifier = Modifier.padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (viewModel.hasPremiumPermission) {
                item {
                    Text(
                        text = "Yaay, you got premium access!",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green)
                            .padding(16.dp)
                    )
                }
            }
            items(viewModel.offerings) { offering ->
                Text(
                    text = offering.offeringID,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            offering.productForID("monthly_sub")
                                ?.let { viewModel.purchase(it, context) }
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

