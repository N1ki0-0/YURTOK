package com.example.yurtok.presentation.screens.filter

import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yurtok.R
import com.example.yurtok.presentation.screens.jod.jodCard.RatingStars

@Composable
fun FilterScreen(
    selectedService: String,
    onServiceChange: (String) -> Unit,
    needsEmployment: Boolean?,
    onNeedsEmploymentChange: (Boolean?) -> Unit,
    freeConsultation: Boolean?,
    onFreeConsultationChange: (Boolean?) -> Unit,
    experienceRange: ClosedFloatingPointRange<Float>,
    onExperienceChange: (ClosedFloatingPointRange<Float>) -> Unit,
    rating: Float?,
    onRatingChange: (Float?) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    onReset: () -> Unit,
    onSubmit: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F6FA))) {
        Scaffold(
            topBar = {
                Surface(
                    elevation = 8.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* Back */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = "Закладка",
                                tint = Color(0xFF6A88F5)
                            )
                        }

                        Text(
                            text = "Фильтры",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF2D2D2D),
                            modifier = Modifier.weight(1f))

                        TextButton(
                            onClick = onReset,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFF6A88F5))
                        ) {
                            Text("Сбросить")
                        }
                    }
                }
            },
            bottomBar = {
                Surface(
                    elevation = 8.dp,
                    color = Color.White
                ) {
                    Button(
                        onClick = onSubmit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A88F5))
                    ) {
                        Text("Показать результаты", fontSize = 16.sp)
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    FilterSection(title = "Специализация") {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("Адвокат", "Юрист", "Нотариус", "Арбитраж")) { type ->
                                FilterChip(
                                    selected = selectedService == type,
                                    text = type,
                                    onSelected = { onServiceChange(type) }
                                )
                            }
                        }
                    }
                }

                item {
                    FilterSection(title = "Стаж работы") {
                        RangeSlider(
                            value = experienceRange,
                            onValueChange = onExperienceChange,
                            valueRange = 0f..30f,
                            steps = 29,
                            modifier = Modifier.padding(vertical = 8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF6A88F5),
                                activeTrackColor = Color(0xFF6A88F5),
                                inactiveTrackColor = Color(0xFFE0E0E0)
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${experienceRange.start.toInt()} лет")
                            Text("${experienceRange.endInclusive.toInt()}+ лет")
                        }
                    }
                }

                item {
                    FilterSection(title = "Рейтинг") {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items((0..4).map { it+1 }) { stars ->
                                RatingChip(
                                    stars = stars,
                                    selected = rating?.toInt() == stars,
                                    onSelected = { onRatingChange(if (it) stars.toFloat() else null) }
                                )
                            }
                        }
                    }
                }

                item {
                    FilterSection(title = "Локация") {
                        OutlinedTextField(
                            value = location,
                            onValueChange = onLocationChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Введите город или регион") },
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color(0xFF6A88F5),
                                unfocusedIndicatorColor = Color(0xFFE0E0E0)
                            ),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.location_on_24dp_e3e3e3),
                                    contentDescription = null,
                                    tint = Color(0xFF6A88F5)
                                )
                            }
                        )
                    }
                }

                item {
                    FilterSection(title = "Дополнительно") {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SwitchRow(
                                text = "Нуждается в трудоустройстве",
                                checked = needsEmployment ?: false,
                                onCheckedChange = { onNeedsEmploymentChange(it.takeIf { it }) }
                            )
                            SwitchRow(
                                text = "Бесплатная консультация",
                                checked = freeConsultation ?: false,
                                onCheckedChange = { onFreeConsultationChange(it.takeIf { it }) }
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun FilterSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF2D2D2D),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FilterChip(selected: Boolean, text: String, onSelected: (Boolean) -> Unit) {
    Surface(
        color = if (selected) Color(0xFF6A88F5) else Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        onClick = { onSelected(!selected) }
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color(0xFF666666),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RatingChip(stars: Int, selected: Boolean, onSelected: (Boolean) -> Unit) {
    Surface(
        color = if (selected) Color(0x336A88F5) else Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        onClick = { onSelected(!selected) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            RatingStars(rating = stars.toFloat(), size = 18.dp)
            Text(
                text = "$stars+",
                color = Color(0xFF666666),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
private fun SwitchRow(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color(0xFF444444),
            modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF6A88F5),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE0E0E0)
            ))
    }
}

@Preview
@Composable
fun fan(){
    // Состояния для фильтров
    var selectedService by remember { mutableStateOf("") }
    var needsEmployment by remember { mutableStateOf<Boolean?>(null) }
    var freeConsultation by remember { mutableStateOf<Boolean?>(null) }
    var experienceRange by remember { mutableStateOf(0f..10f) }
    var rating by remember { mutableStateOf<Float?>(null) }
    var location by remember { mutableStateOf("") }

    // Обработчики изменений
    val onServiceChange = { newService: String ->
        selectedService = newService
    }

    val onNeedsEmploymentChange = { newValue: Boolean? ->
        needsEmployment = newValue
    }

    val onFreeConsultationChange = { newValue: Boolean? ->
        freeConsultation = newValue
    }

    val onExperienceChange = { newRange: ClosedFloatingPointRange<Float> ->
        experienceRange = newRange
    }

    val onRatingChange = { newRating: Float? ->
        rating = newRating
    }

    val onLocationChange = { newLocation: String ->
        location = newLocation
    }

    val onReset = {
        selectedService = ""
        needsEmployment = null
        freeConsultation = null
        experienceRange = 0f..10f
        rating = null
        location = ""
    }

    val onSubmit = {
        // Здесь можно обработать результаты фильтрации
        val filters = mapOf(
            "service" to selectedService,
            "needsEmployment" to needsEmployment,
            "freeConsultation" to freeConsultation,
            "experience" to experienceRange,
            "rating" to rating,
            "location" to location
        )
        println("Применённые фильтры: $filters")
    }

    // Выводим текущие значения фильтров для демонстрации
    Column {
//        Text("Текущие фильтры:", style = MaterialTheme.typography.titleMedium)
//        Text("Специализация: $selectedService")
//        Text("Трудоустройство: ${needsEmployment ?: "не выбрано"}")
//        Text("Консультация: ${freeConsultation ?: "не выбрано"}")
//        Text("Опыт: ${experienceRange.start.toInt()} - ${experienceRange.endInclusive.toInt()} лет")
//        Text("Рейтинг: ${rating?.let { "$it+" } ?: "не выбрано"}")
//        Text("Локация: $location")
//
//        Spacer(modifier = Modifier.height(16.dp))

        // Сам экран фильтрации
        FilterScreen(
            selectedService = selectedService,
            onServiceChange = onServiceChange,
            needsEmployment = needsEmployment,
            onNeedsEmploymentChange = onNeedsEmploymentChange,
            freeConsultation = freeConsultation,
            onFreeConsultationChange = onFreeConsultationChange,
            experienceRange = experienceRange,
            onExperienceChange = onExperienceChange,
            rating = rating,
            onRatingChange = onRatingChange,
            location = location,
            onLocationChange = onLocationChange,
            onReset = onReset,
            onSubmit = onSubmit
        )
    }
}