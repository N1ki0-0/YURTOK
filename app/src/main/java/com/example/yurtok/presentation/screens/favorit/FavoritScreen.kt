package com.example.yurtok.presentation.screens.favorit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.jod.jodCard.JobCard
import com.example.yurtok.presentation.screens.jod.jodCard.JobCardRow
import com.example.yurtok.presentation.screens.jod.jodCard.jobs
import com.example.yurtok.presentation.screens.search.VacanciesViewModel


@Composable
fun FavoritesScreen(navController: NavHostController,
                    viewModel: VacanciesViewModel = hiltViewModel(),
                    favoritesViewModel: FavoritesViewModel = hiltViewModel()) {

    val state by favoritesViewModel.uiState.collectAsState()
    val vacancies by viewModel.vacancies.collectAsState()
    val context = LocalContext.current
    val favoriteIds by favoritesViewModel.favoriteVacancyIds.collectAsState()


    LaunchedEffect(Unit) {
        favoritesViewModel.events.collect{ event ->
            when(event){
                is UiFavoritesEvent.ShowError ->
                    Toast.makeText(context, event.messang, Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        favoritesViewModel.loadFavorites()
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
        state.favorites.isEmpty() -> {
            TextWithIcon()
        }
        else -> {
            //val favoriteVacancies = vacancies.filter { favoriteIds.contains(it.id) }
            val favoriteVacancies = vacancies.filter { it.id in state.favorites }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A88F5),
                            Color(0xFFA478E8)
                        )
                    )
                )
                .padding(16.dp)) {
                Text(text = "Избранное",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "${state.favorites.size} вакансий",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White)
                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn {
                    items(favoriteVacancies) { vacancy ->
                        JobCard(vacancy = vacancy, modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("vacancy/${vacancy.id}")
                            },
                            navController
                        )
                    }
                }

                Spacer(Modifier.height(36.dp))
                Text(text = "популярные вакансии",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Column()
                {

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val modifier = Modifier.width(300.dp).padding(8.dp)
                        items(jobs) { job ->
                            JobCardRow(job, modifier)
                        }
                    }

                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewJobDetailsScreen() {
    val nav = rememberNavController()
    FavoritesScreen(nav)

}