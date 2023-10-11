package com.zelianko.numerologic.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zelianko.numerologic.ui.theme.IconBlue
import com.zelianko.numerologic.ui.theme.LightBlue
import com.zelianko.numerologic.ui.theme.NavBarColor

@Composable
fun ButtonNavigation(
    navController: NavController,
) {
    val listItem = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2
    )
    NavigationBar(
        containerColor = NavBarColor,
        contentColor = Color.White,
    ) {
        val backStateEntry by navController.currentBackStackEntryAsState()
        val currentRow = backStateEntry?.destination?.route
        listItem.forEach { bottomItem ->
            BottomNavigationItem(
                selected = currentRow == bottomItem.route,
                onClick = {
                    navController.navigate(bottomItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomItem.iconId),
                        contentDescription = "Icon",
                        tint = Color.White
                    )
                },
                label = {
                    Text(text = bottomItem.title, fontSize = 12.sp)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White
            )
        }

    }
}