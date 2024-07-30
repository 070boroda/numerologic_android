package com.zelianko.numerologic.activiti

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.zelianko.kitchencalculator.constants.StringConstants.Companion.dak_screen
import com.zelianko.numerologic.R
import com.zelianko.numerologic.ads.AdmobBanner
import com.zelianko.numerologic.ads.BannerInline
import com.zelianko.numerologic.ads.BannerSticky
import com.zelianko.numerologic.ui.theme.DarkBlue
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

    val showDialog = remember { mutableStateOf(false) }

    var popupControl by remember { mutableStateOf(false) }

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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isActiveSub.value == false) {
                BannerSticky(id = R.string.banner_2)
                AdmobBanner(textId = dak_screen)
            }
            if (isActiveSub.value != true) {
                SubInformation()

                if (popupControl) {
                    Popup(
                        alignment = Alignment.TopStart,
                    ) {
                        SubPurScreen(
                            paddingValues = paddingValues,
                            billingViewModel = billingViewModel
                        ) {
                            popupControl = false
                        }
                    }
                }

                if (showDialog.value == true) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        billingViewModel = billingViewModel
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                        onClick = {
                            popupControl = true
                        }
                    ) {
                        Text(
                            text = "Подробнее",
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                BannerInline("R-M-3095140-11", 3)
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
                        TextBlockForDak(header = item, matrix = matrix.value)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
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
            title = "Здор ${matrix?.get("Здоровье")}/Память ${matrix?.get("Память")}/Долг ${matrix?.get("Долг")}/Ин ${matrix?.get("Интерес")}"
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
        26 -> {
            index = stringResource(R.string.one_amb)
            title = "Интерес ${matrix?.get("Интерес")} + Логика ${matrix?.get("Логика")}"
        }
        27 -> {
            index = stringResource(R.string.two_amb)
            title = "Интерес ${matrix?.get("Интерес")} + Быт ${matrix?.get("Быт")}"
        }
        28 -> {
            index = stringResource(R.string.three_amb)
            title = "Темперамент ${matrix?.get("Темперамент")} + Цель ${matrix?.get("Цель")}"
        }
        29 -> {
            index = stringResource(R.string.four_amb)
            title = "Характер ${matrix?.get("Характер")} + Долг ${matrix?.get("Долг")}"
        }
        30 -> {
            index = stringResource(R.string.five_amb)
            title = "Темп.${matrix?.get("Темперамент")}/Эн.${matrix?.get("Энергия")}/Долг${matrix?.get("Долг")}/Семья${matrix?.get("Семья")}"
        }
        31 -> {
            index = stringResource(R.string.six_amb)
            title = "Темп.${matrix?.get("Темперамент")}/Дл.${matrix?.get("Долг")}/Семья${matrix?.get("Семья")},Цель/Скрытая цель"
        }
        32 -> {
            index = stringResource(R.string.seven_amb)
            title = "Семья ${matrix?.get("Семья")} + Быт ${matrix?.get("Быт")}, Цель/Скрытая цель"
        }
        33 -> {
            index = stringResource(R.string.eight_amb)
            title = "Трудоголик"
        }
        34 -> {
            index = stringResource(R.string.dis_amb_one)
            title = "Интерес ${matrix?.get("Интерес")} + Темперамент ${matrix?.get("Темперамент")}"
        }
        35 -> {
            index = stringResource(R.string.dis_amb_two)
            title = "Долг ${matrix?.get("Долг")} + Семья ${matrix?.get("Семья")}"
        }
        36 -> {
            index = stringResource(R.string.dis_amb_three)
            title = "Логика ${matrix?.get("Логика")} + Память ${matrix?.get("Память")} + Семья ${matrix?.get("Семья")}"
        }
        37 -> {
            index = stringResource(R.string.dis_amb_foure)
            title = "Быт ${matrix?.get("Быт")} + Семья ${matrix?.get("Семья")}"
        }
//        CODE
        38 -> {
            index = stringResource(R.string.code_one)
            title = "Код успешности"
        }
        39 -> {
            index = stringResource(R.string.code_two)
            title = "Донор четырех энергий"
        }
        40 -> {
            index = stringResource(R.string.code_three)
            title = "Тиски"
        }
        41 -> {
            index = stringResource(R.string.code_foure)
            title = "Тиски с агрессией"
        }
        42 -> {
            index = stringResource(R.string.code_five)
            title = "Перфекционист"
        }
        43 -> {
            index = stringResource(R.string.code_six)
            title = "Перфекционизм"
        }
        44 -> {
            index = stringResource(R.string.code_seven)
            title = "Замороженность чувств"
        }
        45 -> {
            index = stringResource(R.string.code_eight)
            title = "Родительские коды"
        }
        46 -> {
            index = stringResource(R.string.code_nine)
            title = "Коды Ипохондрика"
        }
        47 -> {
            index = stringResource(R.string.code_ten)
            title = "Писательский код"
        }
        48 -> {
            index = stringResource(R.string.code_eleven)
            title = "Сильная эмоциональность"
        }
        49 -> {
            index = stringResource(R.string.code_twelve)
            title = "Брачный инстинкт"
        }

        50 -> {
            index = stringResource(R.string.code_13)
            title = "Магическое мышление"
        }
        51 -> {
            index = stringResource(R.string.code_14)
            title = "Рисковый код"
        }
        52 -> {
            index = stringResource(R.string.code_15)
            title = "Рисковый код + "
        }
        53 -> {
            index = stringResource(R.string.code_16)
            title = "Генератор идей"
        }
        54 -> {
            index = stringResource(R.string.code_17)
            title = "Скептик"
        }
        55 -> {
            index = stringResource(R.string.code_18)
            title = "Жизнь не учит"
        }
        56 -> {
            index = stringResource(R.string.code_19)
            title = "Код кверулянта или сутяжный синдром"
        }
        57 -> {
            index = stringResource(R.string.code_20)
            title = "«Код зодчества» или Созидатель"
        }
        58 -> {
            index = stringResource(R.string.code_21)
            title = "Код популярности"
        }
        59 -> {
            index = stringResource(R.string.code_22)
            title = "Трудоголизм"
        }
        60 -> {
            index = stringResource(R.string.code_23)
            title = "Трудоголизм +"
        }
        61 -> {
            index = stringResource(R.string.code_24)
            title = "Темная воронка"
        }
        62 -> {
            index = stringResource(R.string.code_25)
            title = "Мегера"
        }
        63 -> {
            index = stringResource(R.string.code_26)
            title = "Все дураки"
        }
        64 -> {
            index = stringResource(R.string.code_27)
            title = "Накопительство"
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
                    ДИССОНАНСОВ,
                    АМБИВАЛЕНТНОСТИ
                    и
                    КОДОВ
                    оформите подписку
                         
                    """.trimIndent(),
            // modifier = Modifier.padding(all = 20.dp),
            style = TextStyle(fontSize = 24.sp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}