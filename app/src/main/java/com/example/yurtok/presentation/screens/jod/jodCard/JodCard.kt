package com.example.yurtok.presentation.screens.jod.jodCard

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yurtok.R


data class JobDetails(
    val id: Int,
    val icon: Int,
    val name: String,
    val serviceType: String,
    val serviceSubType: String,
    val rating: Float,
    val address: String,
    val experienceYears: Int,
    val needsEmployment: Boolean,
    val freeConsultation: Boolean,
    val workDays: String,
    val description: String,
    val email: String,
    val phone: String,
    val priceList: List<Pair<String, String>>,
    val tags: List<String> = emptyList()
)

// Примеры данных
val sampleJobs = listOf(
    JobDetails(
        id = 1,
        icon = R.drawable.skn,
        name = "Иван Иванов",
        serviceType = "Юрист",
        serviceSubType = "Трудовое право",
        rating = 4.8f,
        address = "Москва, ул. Нагорская",
        experienceYears = 5,
        needsEmployment = true,
        freeConsultation = true,
        workDays = "Пн–Пт",
        description = "Профессиональный юрист с опытом в трудовом праве...",
        email = "ivan@example.com",
        phone = "+7 999 123-45-67",
        priceList = listOf(
            "Сделки с недвижимостью" to "5000 ₽",
            "Жилищные споры" to "10000 ₽"
        ),
        tags = listOf("Трудовые споры", "Увольнение")
    ),
    JobDetails(
        id = 2,
        icon = R.drawable.gur,
        name = "Мария Петрова",
        serviceType = "Адвокат",
        serviceSubType = "Уголовное право",
        rating = 4.5f,
        address = "Санкт-Петербург, Невский пр.",
        experienceYears = 8,
        needsEmployment = false,
        freeConsultation = false,
        workDays = "Пн–Сб",
        description = "Опытный адвокат по уголовным делам...",
        email = "maria@example.com",
        phone = "+7 912 345-67-89",
        priceList = listOf(
            "Защита в суде" to "15000 ₽",
            "Консультация" to "3000 ₽"
        ),
        tags = listOf("Уголовные дела", "Апелляции")
    )
)

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
fun JobCard(job: Job, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = job.icon), contentDescription = "Фото",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = job.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Row {
                    Text(text = job.company, fontSize = 14.sp, color = Color.Gray)
                    if (job.isSponsored) {
                        Text(
                            text = " Спонсировано",
                            fontSize = 14.sp,
                            color = colorResource(R.color.teal_200),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(text = job.location, fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(4.dp))

                // 🔹 Синие блоки (Стаж + Трудоустройство)
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(colorResource(R.color.teal_200), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = "Стаж: ${job.experienceYears} лет", color = Color.White, fontSize = 12.sp)
                    }

                    if (job.needsEmployment) {
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
                RatingStars(rating = job.rating)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Icon(
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = "Закладка",
                    tint = colorResource(R.color.black),
                    modifier = Modifier.size(24.dp)
                )

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
    val job = jobs.get(2)
    val modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    JobCard(job, modifier)
}