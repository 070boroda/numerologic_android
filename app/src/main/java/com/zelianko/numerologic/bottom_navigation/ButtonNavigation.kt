package com.zelianko.numerologic.bottom_navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zelianko.numerologic.ui.theme.NavBarColor

@Composable
fun ButtonNavigation(
    navController: NavController,
) {
    val listItem = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
    )
    NavigationBar(
        modifier = Modifier.fillMaxHeight(0.08f),
        containerColor = NavBarColor,
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
                    )
                },
                label = {
                    Text(
                        text = bottomItem.title,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                },
                selectedContentColor = Color.Gray,
                unselectedContentColor = Color.White
            )
        }

    }
}