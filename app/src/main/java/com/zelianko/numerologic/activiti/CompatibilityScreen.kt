package com.zelianko.numerologic.activiti

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.zelianko.numerologic.R
import com.zelianko.numerologic.ads.AdmobBanner
import com.zelianko.numerologic.ads.Banner
import com.zelianko.numerologic.services.CountNumberServices
import com.zelianko.numerologic.ui.theme.Clear
import com.zelianko.numerologic.ui.theme.DarkBlue
import com.zelianko.numerologic.ui.theme.LightBlue
import com.zelianko.numerologic.viewmodel.BillingViewModel
import java.time.LocalDate

/**
 * Экран совместимости
 * две матрицы на одном экране
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MutableCollectionMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompatibilityScreen(
    paddingValues: PaddingValues,
    billingViewModel: BillingViewModel
) {
    val dataMap = remember {
        mutableStateOf(hashMapOf<String, String>())
    }
    val dataMapSecond = remember {
        mutableStateOf(hashMapOf<String, String>())
    }

    val isActiveSub = billingViewModel.isActiveSub.observeAsState()


    Scaffold (
        modifier = Modifier.padding(paddingValues)
    ){
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
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Square(dataMap, billingViewModel)
            Spacer(modifier = Modifier.size(5.dp))
            Square(dataMapSecond, billingViewModel)
            Spacer(modifier = Modifier.size(1.dp))

            if (isActiveSub.value != true) {
                Banner(id = R.string.banner_4)
            }
            if (isActiveSub.value != true) {
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Log.d("purchases state", "Ads Google start")
                    AdmobBanner(modifier = Modifier.fillMaxSize())
                    Log.d("purchases state", "Ads Google end")
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable()
fun Square(
    dataMap: MutableState<HashMap<String, String>>,
    billingViewModel: BillingViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.09f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .alpha(1f)
                    .padding(1.dp),
                colors = CardDefaults.cardColors(LightBlue),
                elevation = CardDefaults.cardElevation(5.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Доп.цифры",
                        modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                        style = TextStyle(fontSize = 14.sp),
                        color = Color.White
                    )
                    Text(
                        text = if (dataMap.value.isEmpty()) {
                            "----"
                        } else {
                            dataMap.value["Доп.цифры"].toString()
                        },
                        modifier = Modifier.padding(top = 5.dp, bottom = 8.dp),
                        style = TextStyle(fontSize = 18.sp),
                        color = Color.White
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .alpha(1f)
                    .padding(1.dp),
                colors = CardDefaults.cardColors(LightBlue),
                elevation = CardDefaults.cardElevation(5.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Число судьбы",
                        modifier = Modifier.padding(top = 2.dp),
                        style = TextStyle(fontSize = 12.sp),
                        color = Color.White
                    )
                    Text(
                        text = if (dataMap.value.isEmpty()) {
                            "----"
                        } else {
                            dataMap.value["Число судьбы"].toString()
                        },
                        modifier = Modifier.padding(top = 8.dp, bottom = 15.dp),
                        style = TextStyle(fontSize = 18.sp),
                        color = Color.White
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(1f)
                    .padding(1.dp),
                colors = CardDefaults.cardColors(LightBlue),
                elevation = CardDefaults.cardElevation(5.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Темперамент",
                        modifier = Modifier.padding(top = 2.dp),
                        style = TextStyle(fontSize = 12.sp),
                        color = Color.White
                    )
                    Text(
                        text = if (dataMap.value.isEmpty()) {
                            "----"
                        } else {
                            dataMap.value["Темперамент"].toString()
                        },
                        modifier = Modifier.padding(top = 8.dp, bottom = 15.dp),
                        style = TextStyle(fontSize = 18.sp),
                        color = Color.White
                    )
                }
            }
        }

        SecondLine(
            label1 = "Характер", value1 = dataMap.value["Характер"].toString(),
            label2 = "Здоровье", value2 = dataMap.value["Здоровье"].toString(),
            label3 = "Удача", value3 = dataMap.value["Удача"].toString(),
            label4 = "Цель", value4 = dataMap.value["Цель"].toString(), maxHeightSize = 0.10f
        )

        SecondLine(
            label1 = "Энергия", value1 = dataMap.value["Энергия"].toString(),
            label2 = "Логика", value2 = dataMap.value["Логика"].toString(),
            label3 = "Долг", value3 = dataMap.value["Долг"].toString(),
            label4 = "Семья", value4 = dataMap.value["Семья"].toString(), maxHeightSize = 0.11f
        )

        SecondLine(
            label1 = "Интерес", value1 = dataMap.value["Интерес"].toString(),
            label2 = "Труд", value2 = dataMap.value["Труд"].toString(),
            label3 = "Память", value3 = dataMap.value["Память"].toString(),
            label4 = "Привычки", value4 = dataMap.value["Привычки"].toString(),
            maxHeightSize = 0.125f
        )
        LastClearLine(value2 = dataMap.value["Быт"].toString(), map = dataMap, billingViewModel = billingViewModel)
    }
    }



@Composable
private fun SecondLine(
    label1: String,
    value1: String,
    label2: String,
    value2: String,
    label3: String,
    value3: String,
    label4: String,
    value4: String,
    maxHeightSize: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(maxHeightSize)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .alpha(0.8f)
                .padding(1.dp),
            colors = CardDefaults.cardColors(LightBlue),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label1,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.White
                )
                Text(
                    text = if (value1 == "null") {
                        "----"
                    } else {
                        value1
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                    style = TextStyle(fontSize = 18.sp),
                    color = Color.White
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.335f)
                .alpha(0.8f)
                .padding(1.dp),
            colors = CardDefaults.cardColors(LightBlue),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label2,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.White
                )
                Text(
                    text = if (value2 == "null") {
                        "----"
                    } else {
                        value2
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                    style = TextStyle(fontSize = 18.sp),
                    color = Color.White
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .alpha(0.8f)
                .padding(1.dp),
            colors = CardDefaults.cardColors(LightBlue),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label3,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.White
                )
                Text(
                    text = if (value3 == "null") {
                        "----"
                    } else {
                        value3
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                    style = TextStyle(fontSize = 18.sp),
                    color = Color.White
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(1f)
                .padding(1.dp),
            colors = CardDefaults.cardColors(LightBlue),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label4,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.White
                )
                Text(
                    text = if (value4 == "null") {
                        "----"
                    } else {
                        value4
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                    style = TextStyle(fontSize = 18.sp),
                    color = Color.White
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun LastClearLine(
    value2: String,
    map: MutableState<HashMap<String, String>>,
    billingViewModel: BillingViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.20f)
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .alpha(0.8f)
                .padding(1.dp),
            colors = CardDefaults.cardColors(Clear),
            shape = RoundedCornerShape(10.dp)
        ) {}

        Card(
            modifier = Modifier
                .fillMaxWidth(0.335f)
                .alpha(0.8f)
                .padding(1.dp),
            colors = CardDefaults.cardColors(LightBlue),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Быт",
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.White
                )
                Text(
                    text = if (value2 == "null") {
                        "----"
                    } else {
                        value2
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                    style = TextStyle(fontSize = 18.sp),
                    color = Color.White
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .alpha(1f)
                .padding(5.dp),
            colors = CardDefaults.cardColors(DarkBlue),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Date(map, billingViewModel = billingViewModel)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Date(
    map: MutableState<HashMap<String, String>>,
    billingViewModel: BillingViewModel
) {

    val selectedDateText = remember { mutableStateOf("") }

    val calendarState = rememberUseCaseState(visible = false)

    val isActiveSub = billingViewModel.isActiveSub.observeAsState()
    //state dialog
    val showDialog = remember { mutableStateOf(false) }

    CalendarSample(calendarState = calendarState, selectedDateText = selectedDateText)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedDateText.value.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = selectedDateText.value,
                style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Normal),
                color = Color.White
            )
        }

        if (isActiveSub.value == true) {
            Button(
                modifier = Modifier
                    .fillMaxSize(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                onClick = {
                    calendarState.show()
                }
            ) {
                Text(
                    text = "Дата рождения",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
            }
        } else {
            if (showDialog.value == true) {
                AlertDialog(
                    onDismissRequest  = {showDialog.value = false},
                    billingViewModel = billingViewModel
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxSize(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                onClick = {
                    showDialog.value = true
                }
            ) {
                Text(
                    text = "Оформите подписку",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }

    val mapObject = CountNumberServices()
    map.value = mapObject.countNumber(selectedDateText.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun CalendarSample(calendarState: UseCaseState, selectedDateText: MutableState<String>){

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
            boundary = LocalDate.of(1900, 1, 1)..LocalDate.now()
        ),
        selection = CalendarSelection.Dates { newDates ->
            selectedDateText.value = "${newDates.get(0).dayOfMonth}/${newDates.get(0).month.value}/${newDates.get(0).year}"
        },
    )
}
