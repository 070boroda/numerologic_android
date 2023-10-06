package com.zelianko.numerologic.bottom_navigation

import com.zelianko.numerologic.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {

    object Screen1: BottomItem("Расчет", R.drawable.screen_1_ico, "screen_1")
    object Screen2: BottomItem("Справка", R.drawable.screen_2_ico, "screen_2")
}