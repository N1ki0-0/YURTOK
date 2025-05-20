package com.example.yurtok.presentation.screens.jod.jodCard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.yurtok.R
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.favorit.FavoritesViewModel
import com.example.yurtok.presentation.screens.favorit.UiFavoritesEvent



data class Job(
    val icon: Int,
    val title: String,
    val company: String,
    val location: String,
    val isSponsored: Boolean,
    val experienceYears: Int,  // Стаж работы
    val needsEmployment: Boolean, // Нуждается в трудоустройстве
    val rating: Float, // Оценка (0.0 - 5.0)
)


val jobs = listOf(
    Job(R.drawable.gur, "Лукьянов И.С.", "Бывший следователь", "Москва", false, 3, true, 4.5f),
    Job(R.drawable.skn, "СКН", "Адвокатское бюро", "Санкт-Петербург", false, 5, false, 4.8f),
    Job(R.drawable.arta, "АРТА", "Юридическое бюро", "Казань", false, 2, true, 2.9f),
    Job(R.drawable.gur, "Гурьев и партнеры", "Badoo Inc.", "Новосибирск", true, 1, true, 4.2f),
    Job(R.drawable.skn, "СКН", "Адвокатское бюро", "Екатеринбург", false, 4, false, 4.6f),
    Job(R.drawable.arta, "АРТА", "Юридическое бюро", "Краснодар", false, 6, false, 3.5f),
)

@Composable
fun JobCard(vacancy: Vacancy,
            modifier: Modifier = Modifier,
            navController: NavHostController,
            favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favState by favoritesViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val isAuth = favState.isAuth

    LaunchedEffect(Unit) {
        favoritesViewModel.events.collect{ event ->
            when(event){
                is UiFavoritesEvent.ShowError ->
                    Toast.makeText(context, event.messang, Toast.LENGTH_LONG).show()
            }
        }
    }

    val isFavorite = vacancy.id in favState.favorites

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        //val isFavorite = favoritesViewModel.favoriteVacancyIds.collectAsState().value.contains(vacancy.id)
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = "http://10.0.2.2:8080${vacancy.icon}"),
                contentDescription = "Фото",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = vacancy.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Row {
                    Text(text = vacancy.serviceType, fontSize = 14.sp, color = Color.Gray)

                }

                Text(text = vacancy.address, fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(4.dp))

                // 🔹 Синие блоки (Стаж + Трудоустройство)
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(colorResource(R.color.teal_200), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = "Стаж: ${vacancy.experienceYears} лет", color = Color.White, fontSize = 12.sp)
                    }

                    if (vacancy.needsEmployment) {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .background(Color.Red, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "Ищу работу", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ⭐ Рейтинг (звездочки)
                RatingStars(rating = vacancy.rating)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick =
                {
                    if(isAuth){
                        favoritesViewModel.toggleFavorite(vacancy.id)
                    }else{
                        navController.navigate(Route.LOGIN){
                            navController.popBackStack(Route.SEARCH, inclusive = false)
                        }
                    }
                },

                ) {
                    Icon(
                        painter = painterResource
                            (id = if (isFavorite) R.drawable.bookmark_filled else R.drawable.bookmark),
                        contentDescription = "Закладка",
                        tint = Color(0xFF6A88F5),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun RatingStars(rating: Float, size: Dp = 24.dp) {
    Row {
        repeat(5) { index ->
            val iconRes = when {
                rating >= index + 1 -> R.drawable.star_filled
                rating > index -> R.drawable.star_half
                else -> R.drawable.star_outline
            }

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Рейтинг",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(size)
            )
        }
    }
}

@Preview
@Composable
fun ScreensJod(){
//    val job = jobs.get(2)
//    val modifier = Modifier
//        .fillMaxWidth()
//        .padding(8.dp)
//    JobCard(job, modifier)
}