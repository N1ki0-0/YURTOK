package com.example.yurtok.presentation.screens.application

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.search.VacanciesViewModel

@Composable
fun ApplicationsScreen(
    navController: NavHostController,
    viewModel: ApplicationsViewModel = hiltViewModel(),
    vacancy: VacanciesViewModel = hiltViewModel()
) {

    val applications by viewModel.applications.collectAsState()
    val vacancies by vacancy.vacancies.collectAsState()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiApplicationsEvent.ShowError ->
                    Toast.makeText(context, event.messange, Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadApplications()
    }

    when{
        state.isLoading ->{ // Сделать экран загрузки в формате Shimmer
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        !state.isAuth -> {
            navController.navigate(Route.LOGIN){
                navController.popBackStack(Route.SEARCH, inclusive = false)
            }
        }
        state.applications.isEmpty() ->{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Вы ещё не откликнулись на вакансии",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }else ->{
            val vacancyNameMap = vacancies.associate { it.id to it.name }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF6A88F5), Color(0xFFA478E8))
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Мои отклики",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.applications) { application ->
                        val vacancyName = vacancyNameMap.getOrDefault(application.vacancyId, "Неизвестная вакансия")
                        ApplicationCard(application, vacancyName)
                    }
                }
            }
        }
    }
}