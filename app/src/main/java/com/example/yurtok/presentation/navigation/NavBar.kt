package com.example.yurtok.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.R
import kotlinx.coroutines.selects.select

@Composable
fun NavBar(navController: NavHostController, isShowBottomBar: MutableState<Boolean>){

    val navItem = listOf(
        BottonNavItem.Search,
        BottonNavItem.Favorites,
        BottonNavItem.Profile
    )

    if(isShowBottomBar.value) {
        BottomNavigation(backgroundColor = colorResource(id = R.color.black),
            contentColor = Color.White
        ){
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            navItem.forEach{items ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = if(currentRoute == items.route) items.iconFocused else items.icon),
                            contentDescription = items.title
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == items.route,
                    onClick = { navController.navigate(items.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun Okk(){
    val navController = rememberNavController()
    val isShowBottomBar = remember { mutableStateOf(true) }
    NavBar(navController, isShowBottomBar)
}