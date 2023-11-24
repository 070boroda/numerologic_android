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

    val matrix = viewModel.commonMatrix.observeAsState()

    Scaffold(
        modifier = Modifier.padding(paddingValues)
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
                    dataList.value,
                ) { _, item ->
                    TextBlockForDak(header = item, matrix =  matrix.value)
                }
            }
            }
        }
    }
}

@Composable
fun TextBlockForDak(header: Int, matrix: HashMap<String, String>?) {
    var index = ""
    var title = ""
    when (header) {
        1 -> {
            index = stringResource(R.string.one_dis)
            title = "Логика ${matrix?.get("Логика")} + Память ${matrix?.get("Память")}"
        }
        2 -> {
            index = stringResource(R.string.two_dis)
            title = "Здоровье ${matrix?.get("Здоровье")} + Память ${matrix?.get("Память")}"
        }
        3 -> {
             index = stringResource(R.string.three_dis)
            title = "Долг ${matrix?.get("Долг")} + Память ${matrix?.get("Память")}"
        }
        4 -> {
            index = stringResource(R.string.four_dis)
            title = "Логика ${matrix?.get("Логика")} + Память ${matrix?.get("Память")} + Семья ${matrix?.get("Семья")}"
        }
        5 -> {
            index = stringResource(R.string.five_dis)
            title = "Быт ${matrix?.get("Быт")} + Долг ${matrix?.get("Долг")}"
        }
        6 -> {
            index = stringResource(R.string.six_dis)
            title = "Число судьбы ${matrix?.get("Число судьбы")} + Семья ${matrix?.get("Семья")}"
        }
        7 -> {
            index = stringResource(R.string.seven_dis)
            title = "Скрытая цель/Цель + Семья ${matrix?.get("Семья")}"
        }
        8 -> {
            index = stringResource(R.string.eight_dis)
            title = "Цель ${matrix?.get("Цель")} + Семья ${matrix?.get("Семья")}"
        }
        9 -> {
            index = stringResource(R.string.nime_dis)
            title = "Характер ${matrix?.get("Характер")} + Быт ${matrix?.get("Быт")}"
        }
        10 -> {
            index = stringResource(R.string.ten_dis)
            title = "Энергия ${matrix?.get("Энергия")} + Привычки ${matrix?.get("Привычки")}"
        }
        11 -> {
            index = stringResource(R.string.elevan_dis)
            title = "Энергия ${matrix?.get("Энергия")} + Цель ${matrix?.get("Цель")}"
        }
        12 -> {
            index = stringResource(R.string.twelve_dis)
            title = "Энергия ${matrix?.get("Энергия")} + Логика ${matrix?.get("Логика")}"
        }
        13 -> {
            index = stringResource(R.string.thirteen_dis)
            title = "Энергия ${matrix?.get("Энергия")} + Память ${matrix?.get("Память")}"
        }
        14 -> {
            index = stringResource(R.string.fourteen_dis)
            title = "Семья ${matrix?.get("Семья")} + Быт ${matrix?.get("Быт")}"
        }
        15 -> {
            index = stringResource(R.string.fifthteen_dis)
            title = "Цель ${matrix?.get("Цель")} + Быт ${matrix?.get("Быт")} + Семья ${matrix?.get("Семья")}"
        }
        16 -> {
            index = stringResource(R.string.sixteen_dis)
            title = "Логика ${matrix?.get("Логика")} + Интерес ${matrix?.get("Интерес")}"
        }
        17 -> {
            index = stringResource(R.string.seventeen_dis)
            title = "Логика ${matrix?.get("Логика")} + Цель ${matrix?.get("Цель")}"
        }
        18 -> {
            index = stringResource(R.string.eighteen_dis)
            title = "Долг ${matrix?.get("Долг")} + Цель ${matrix?.get("Цель")}"
        }
        19 -> {
            index = stringResource(R.string.nineteen_dis)
            title = "Труд ${matrix?.get("Труд")} + Семья ${matrix?.get("Семья")}"
        }
        20 -> {
            index = stringResource(R.string.twenty_dis)
            title = "Судьбы ${matrix?.get("Судьбы")} + Долг ${matrix?.get("Долг")}"
        }
        21 -> {
            index = stringResource(R.string.twenty_one_dis)
            title = "Здоровье ${matrix?.get("Здоровье")} + Долг ${matrix?.get("Долг")}"
        }
        22 -> {
            index = stringResource(R.string.twenty_two_dis)
            title = "Здоровье ${matrix?.get("Здоровье")}/Память ${matrix?.get("Память")}/Долг ${matrix?.get("Долг")}/Интерес ${matrix?.get("Интерес")}"
        }
        23 -> {
            index = stringResource(R.string.twenty_three_dis)
            title = "Судьбы ${matrix?.get("Судьбы")} + Память ${matrix?.get("Память")}"
        }
        24 -> {
            index = stringResource(R.string.twenty_four_dis)
            title = "Судьбы ${matrix?.get("Судьбы")} + Цель/Скрытая цель}"
        }
        25 -> {
            index = stringResource(R.string.twenty_five_dis)
            title = "Темперамент ${matrix?.get("Темперамент")} + Семья ${matrix?.get("Семья")}"
        }
    }

    var content: String = index
    content = content.replace("[", "").replace("]", "")

    ExpandableCard(
        title = title,
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
                    оформите подписку
                    В ближайшее время будет
                    добавлен расчет
                    АМБИВАЛЕНТНОСТИ
                    и
                    КОДЫ
                         
                    """.trimIndent(),
            // modifier = Modifier.padding(all = 20.dp),
            style = TextStyle(fontSize = 24.sp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}