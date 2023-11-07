package com.zelianko.numerologic.activiti

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelianko.numerologic.R
import com.zelianko.numerologic.ads.AdmobBanner
import com.zelianko.numerologic.ads.Banner
import com.zelianko.numerologic.ui.theme.LightBlue
import com.zelianko.numerologic.viewmodel.BillingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HelpScreen(
    paddingValues: PaddingValues,
    billingViewModel: BillingViewModel
) {

    val isActiveSub = billingViewModel.isActiveSub.observeAsState()
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
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Log.d("purchases state", "Ads Google start")
                    AdmobBanner(modifier = Modifier.fillMaxSize())
                    Log.d("purchases state", "Ads Google end")
                }
            }
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
                    listOf(
                        "Общие сведения",
                        "ХАРАКТЕР",
                        "ЭНЕРГИЯ",
                        "ИНТЕРЕС",
                        "ЗДОРОВЬЕ",
                        "ЛОГИКА",
                        "ТРУД",
                        "УДАЧА",
                        "ДОЛГ",
                        "ПАМЯТЬ",
                        "ТЕМПЕРАМЕНТ",
                        "ЦЕЛЬ",
                        "СЕМЬЯ",
                        "БЫТ",
                        "ПРИВЫЧКИ",
                        "ЧИСЛО СУДЬБЫ"
                    )
                ) { _, item ->
                    TextBlock(header = item)
                }
            }
        }
    }
}

/**
 * Формируем блоки
 * заголовок, и текст
 */
@Composable
fun TextBlock(header: String) {
    val stringArray: Array<String>
    when (header) {
        "Общие сведения" -> stringArray = stringArrayResource(R.array.headers)
        "ХАРАКТЕР" -> stringArray = stringArrayResource(R.array.character)
        "ЭНЕРГИЯ" -> stringArray = stringArrayResource(R.array.energy)
        "ИНТЕРЕС" -> stringArray = stringArrayResource(R.array.interes)
        "ЗДОРОВЬЕ" -> stringArray = stringArrayResource(R.array.health)
        "ЛОГИКА" -> stringArray = stringArrayResource(R.array.logik)
        "ТРУД" -> stringArray = stringArrayResource(R.array.trud)
        "УДАЧА" -> stringArray = stringArrayResource(R.array.fart)
        "ДОЛГ" -> stringArray = stringArrayResource(R.array.dolg)
        "ПАМЯТЬ" -> stringArray = stringArrayResource(R.array.memory)
        "ТЕМПЕРАМЕНТ" -> stringArray = stringArrayResource(R.array.temperament)
        "ЦЕЛЬ" -> stringArray = stringArrayResource(R.array.cell)
        "СЕМЬЯ" -> stringArray = stringArrayResource(R.array.famaly)
        "БЫТ" -> stringArray = stringArrayResource(R.array.bit)
        "ПРИВЫЧКИ" -> stringArray = stringArrayResource(R.array.privichki)
        "ЧИСЛО СУДЬБЫ" -> stringArray = stringArrayResource(R.array.sudba)
        else -> {
            stringArray = emptyArray()
        }
    }

    var content:String = stringArray.contentToString()
    content = content.replace("[", "").replace("]", "")

    ExpandableCard(title = header,
        content = content)
    Spacer(Modifier.height(15.dp))
}

/**
 * Заголовок
 */
@Composable
fun Header(value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = Bold,
            fontFamily = FontFamily.Default,
        )
    }
}

/**
 * Текст сообщения
 */
@Composable
fun InfoTextBlock(value: String) {
    Text(
        text =  value,
        fontSize = 12.sp,
        color = Color.White,
        fontWeight = Normal,
        fontFamily = FontFamily.Default,
        style = TextStyle(textIndent = TextIndent(20.sp, 0.sp))
    )
}


@Composable
fun ExpandableCard(title:String, content:String) {

    var expanded = remember { mutableStateOf (false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(LightBlue),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                expanded.value = !expanded.value
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(15.dp),
                text = title,
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = Normal,
                fontFamily = FontFamily.Default,
                style = TextStyle(textIndent = TextIndent(20.sp, 0.sp))
            )
            if (expanded.value) {
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 5.dp),
                    text = content,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = Normal,
                    fontFamily = FontFamily.Default,
                    style = TextStyle(textIndent = TextIndent(20.sp, 0.sp))
                )
            }
        }
    }
}

