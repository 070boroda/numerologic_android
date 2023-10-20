package com.zelianko.numerologic.bottom_navigation

import com.zelianko.numerologic.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {

    object Screen1: BottomItem("Расчет", R.drawable.raschet_nav_bar_ico, "screen_1")
    object Screen2: BottomItem("Справка", R.drawable.info_nav_bar_icon, "screen_2")
    object Screen3: BottomItem("Совместимость", R.drawable.two_cell_nav_bar_icon, "screen_3")
    object Screen4: BottomItem("Test", R.drawable.two_cell_nav_bar_icon, "screen_4")
}