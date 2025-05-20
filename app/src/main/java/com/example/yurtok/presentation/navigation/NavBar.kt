package com.example.yurtok.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.R
import com.example.yurtok.presentation.screens.profile.ProfileViewModel
import kotlinx.coroutines.selects.select

@Composable
fun NavBar(navController: NavHostController,
           isShowBottomBar: MutableState<Boolean>,
           profileViewModel: ProfileViewModel = hiltViewModel()
){
    val user by profileViewModel.user.collectAsState()

    val navItem = listOf(
        BottonNavItem.Search,
        BottonNavItem.Favorites,
        BottonNavItem.Application,
        BottonNavItem.Profile
    )

    if(isShowBottomBar.value) {
        BottomNavigation(backgroundColor = colorResource(id = R.color.white),
            contentColor = Color.Black
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
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == items.route,
                    onClick = { val destinationRoute = if (items is BottonNavItem.Profile && user == null) {
                        Route.LOGIN  // Если пользователь не авторизован, открываем регистрацию
                    } else {
                        items.route
                    }
                        navController.navigate(destinationRoute) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
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