package com.example.yurtok.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.R
import com.example.yurtok.presentation.screens.application.ApplicationsScreen
import com.example.yurtok.presentation.screens.favorit.FavoritesScreen
import com.example.yurtok.presentation.screens.jod.jodCard.VacancyDetailViewModel
import com.example.yurtok.presentation.screens.jod.jodScreen.JobScreen
import com.example.yurtok.presentation.screens.login.LoginScreen
import com.example.yurtok.presentation.screens.profile.ProfileScreen
import com.example.yurtok.presentation.screens.search.SearchScreen
import com.example.yurtok.presentation.screens.signup.SignupScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val isShowBottomBar = remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavBar(navController, isShowBottomBar)
        },
        content = {
            NavHost(modifier = Modifier.padding(it),
                navController = navController,
                startDestination = Route.SEARCH
            ){
                composable(Route.LOGIN){
                    isShowBottomBar.value = false
                    LoginScreen(navController)
                }
                composable(Route.SIGNUP){
                    isShowBottomBar.value = false
                    SignupScreen(navController)
                }
                composable(Route.SEARCH){
                    isShowBottomBar.value = true
                    SearchScreen(navController)
                }
                composable(Route.PROFILE){
                    isShowBottomBar.value = true
                    ProfileScreen(navController)
                }
                composable(Route.FAVOURITES) {
                    isShowBottomBar.value = true
                    FavoritesScreen(navController)
                }
                composable(Route.APPLICATION) {
                    isShowBottomBar.value = true
                    ApplicationsScreen(navController)
                }

                composable("vacancy/{vacancyId}") { backStackEntry ->
                    isShowBottomBar.value = false
                    val vacancyId = backStackEntry.arguments?.getString("vacancyId")?.toIntOrNull()

                    if (vacancyId != null) {
                        val viewModel: VacancyDetailViewModel = hiltViewModel()
                        val vacancy by viewModel.vacancy.collectAsState()

                        LaunchedEffect(Unit) {
                            viewModel.loadVacancy(vacancyId)
                        }

                        vacancy?.let {
                            JobScreen(vacancy = it, navController)
                        }
                    }
                }
            }
        })
}