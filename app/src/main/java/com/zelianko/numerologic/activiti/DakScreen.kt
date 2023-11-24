package com.zelianko.numerologic.activiti

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelianko.numerologic.R
import com.zelianko.numerologic.ads.AdmobBanner
import com.zelianko.numerologic.ads.Banner
import com.zelianko.numerologic.viewmodel.BillingViewModel
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DakScreen(
    viewModel: SelectedDateTextViewModel,
    paddingValues: PaddingValues,
    billingViewModel: BillingViewModel
) {
    val dataList = viewModel.listDis.observeAsState(mutableListOf())
    val isActiveSub = billingViewModel.isActiveSub.observeAsState()
    Scaffold(
        modifier = androidx.compose.ui.Modifier.padding(paddingValues)
    ) {
        Image(
            painter = painterResource(id = R.drawable.screen_1),
            contentDescription = "image1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isActiveSub.value == false) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Banner(id = R.string.banner_2)
                }
            }

            if (isActiveSub.value != true) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Log.d("purchases state", "Ads Google start")
                    AdmobBanner(modifier = Modifier.fillMaxSize())
                    Log.d("purchases state", "Ads Google end")
                }
            }
            if (isActiveSub.value != true) {
                SubInformation()
            } else {


            LazyColumn(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 15.dp,
                        top = 10.dp,
                    )
                    .fillMaxWidth()
            ) {
                itemsIndexed(
                    dataList.value
                ) { _, item ->
                    TextBlockForDak(header = item)
                }
            }
            }
        }
    }
}

@Composable
fun TextBlockForDak(header: Int) {
    var index: String = ""
    when (header) {
        1 -> index = stringResource(R.string.one_dis)
        2 -> index = stringResource(R.string.two_dis)
        3 -> index = stringResource(R.string.three_dis)
        4 -> index = stringResource(R.string.four_dis)
        5 -> index = stringResource(R.string.five_dis)
        6 -> index = stringResource(R.string.six_dis)
        7 -> index = stringResource(R.string.seven_dis)
        8 -> index = stringResource(R.string.eight_dis)
        9 -> index = stringResource(R.string.nime_dis)
        10 -> index = stringResource(R.string.ten_dis)
        11 -> index = stringResource(R.string.elevan_dis)
        12 -> index = stringResource(R.string.twelve_dis)
        13 -> index = stringResource(R.string.thirteen_dis)
        14 -> index = stringResource(R.string.fourteen_dis)
        15 -> index = stringResource(R.string.fifthteen_dis)
        16 -> index = stringResource(R.string.sixteen_dis)
        17 -> index = stringResource(R.string.seventeen_dis)
        18 -> index = stringResource(R.string.eighteen_dis)
        19 -> index = stringResource(R.string.nineteen_dis)
        20 -> index = stringResource(R.string.twenty_dis)
        21 -> index = stringResource(R.string.twenty_one_dis)
        22 -> index = stringResource(R.string.twenty_two_dis)
        23 -> index = stringResource(R.string.twenty_three_dis)
        24 -> index = stringResource(R.string.twenty_four_dis)
        25 -> index = stringResource(R.string.twenty_five_dis)
    }

    var content: String = index
    content = content.replace("[", "").replace("]", "")

    ExpandableCard(
        title = header.toString(),
        content = content
    )
    Spacer(Modifier.height(15.dp))
}

@Composable
fun SubInformation(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = """
                    Для расшифровки 
                    ДИССОНАНСОВ
                    АМБИВАЛЕНТНОСТИ
                    оформите подписку
                              
                    """.trimIndent(),
            // modifier = Modifier.padding(all = 20.dp),
            style = TextStyle(fontSize = 24.sp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}