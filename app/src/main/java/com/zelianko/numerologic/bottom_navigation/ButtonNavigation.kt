package com.zelianko.numerologic.bottom_navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zelianko.numerologic.ui.theme.NavBarColor

@Composable
fun ButtonNavigation(
    navHostController: NavHostController,

) {
    val listItem = listOf(
        BottomItem.Screen1,
        BottomItem.Screen3,
        BottomItem.Screen2,
        BottomItem.Screen4,
    )
    NavigationBar(
        modifier = Modifier.fillMaxHeight(0.08f),
        containerColor = NavBarColor,
    ) {

        val navigationState = remember {
            NavigationState(navHostController)
        }
        val backStateEntry by navHostController.currentBackStackEntryAsState()
        val currentRow = backStateEntry?.destination?.route
        listItem.forEach { bottomItem ->
            BottomNavigationItem(
                selected = currentRow == bottomItem.route,
                onClick = {
                    navigationState.navigateTo(bottomItem.route)
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