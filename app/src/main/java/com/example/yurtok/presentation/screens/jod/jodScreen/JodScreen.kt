package com.example.yurtok.presentation.screens.jod.jodScreen

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.constraintlayout.helper.widget.Grid
import com.example.yurtok.R
import com.example.yurtok.presentation.screens.jod.jodCard.Job
import com.example.yurtok.presentation.screens.jod.jodCard.RatingStars
import com.example.yurtok.presentation.screens.jod.jodCard.jobs
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun JobScreen(name: String = "Иван Иванов",
              serviceType: String = "Юрист",
              serviceSubType: String = "Трудовое право",
              rating: Float = 4.8f,
              address: String = "Москва, ул. Нагорская",
              experience: String = "5 лет",
              needsEmployment: Boolean = true,
              freeConsultation: Boolean = true,
              workDays: String = "Пн–Пт",
              description: String = "Добpый день! Mеня зовут Иван! Оказываю профeсcионaльныe юридичeские уcлуги по гpaждaнcкoму праву. 20 лет юpидическoгo стажa.\n" +
                      "\n" +
                      "✔\uFE0F Защита прав потребитeлей; \n" +
                      "✔\uFE0F Cпоpы, cвязанныe c нeдвижимocтью; \n" +
                      "✔\uFE0F Споры с зaстpoйщикaми (взыcкание cтрoитeльных недоcтатков и неуcтoйки, раcторжeниe ДДУ); \n" +
                      "✔\uFE0F Bзыcкaние дoлгов, неустойки, морального вреда \n" +
                      "по распискам, договорам, с учётом пени, штрафов, судебных расходов; \n" +
                      "✔\uFE0F Арендные споры; \n" +
                      "✔\uFE0F Жилищные споры (признание права, выселения/вселения, право пользования квартирой, утрата, выписка из квартиры через суд, расторжения договоров купли-продажи, найма и пр.); \n" +
                      "✔\uFE0F Споры со страховыми компаниями, не выплата страхового возмещения, возврат уплаченной страховой премии за навязанную страховку, взыскание денежных средств при занижении страховой суммы, взыскание с виновника ДТП, проценты, штрафы, неустойки \n" +
                      "✔\uFE0F Споры с банками по кредитам; \n" +
                      "✔\uFE0F Семейные споры (раздел совместного имущества, определение порядка общения, брачный договор, алименты и др.) \n" +
                      "✔\uFE0F Трудовые споры (взыскание зарплаты, неустойки, восстановление на работе, признание приказа об увольнении незаконным и др.); \n" +
                      "✔\uFE0F Арбитражные споры для юридических лиц и предпринимателей. \n" +
                      "✔\uFE0F Исполнительное производство.",
              email: String = "ivan@example.com",
              phone: String = "+7 999 123-45-67",
              priceList: List<Pair<String, String>> = listOf(
                  "Сделки с недвижимостью" to "5000 ₽",
                  "Жилищные споры" to "10000 ₽"
              )
) {
    var showConfirmation by remember { mutableStateOf(false) }
    val buttonText = if (showConfirmation) "Вам скоро ответят" else "Связаться"
    val buttonColor by animateColorAsState(
        targetValue = if (showConfirmation) Color(0xFF4CAF50) else Color(0xFF6A88F5),
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A88F5),
                        Color(0xFFA478E8)
                    )
                )
            )
    ) {
        Scaffold(
            bottomBar = {
                Button(
                    onClick = { showConfirmation = true },
                    enabled = !showConfirmation,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        disabledContainerColor = buttonColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    AnimatedContent(
                        targetState = buttonText,
                        transitionSpec = {
                            slideInVertically { height -> height } + fadeIn() with
                                    slideOutVertically { height -> -height } + fadeOut()
                        }
                    ) { text ->
                        Text(
                            text = text,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(bottom = 16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            // 🔹 Header
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .shadow(8.dp, CircleShape)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.cat_),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color(0xFF2D2D2D)
                                    )
                                    Text(
                                        text = "$serviceType • $serviceSubType",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF666666)
                                    )
                                    RatingStars(rating = rating)
                                }

                                IconButton(
                                    onClick = { /* TODO: Add to favorites */ },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.bookmark),
                                        contentDescription = "Закладка",
                                        tint = Color(0xFF6A88F5)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // 🔹 Location Chip
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.location_on_24dp_e3e3e3),
                                    contentDescription = "Location",
                                    tint = Color(0xFF6A88F5),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = address,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF444444)
                                )
                            }

                            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                            // 🔹 Details Grid
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .height(150.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(listOf(
                                    "Опыт работы" to experience,
                                    "Трудоустройство" to if (needsEmployment) "Нужно" else "Не нужно",
                                    "Консультация" to if (freeConsultation) "Бесплатно" else "Платно",
                                    "График" to workDays
                                )) { (title, value) ->
                                    GridItem(title = title, value = value)
                                }
                            }

                            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                            // 🔹 Price List
                            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                                Text(
                                    text = "Услуги и цены",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF2D2D2D)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                priceList.forEach { (service, price) ->
                                    PriceListItem(service = service, price = price)
                                }
                            }

                            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                            // 🔹 Description
                            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                                Text(
                                    text = "О специалисте",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF2D2D2D)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF666666),
                                    lineHeight = 20.sp
                                )
                            }

                            // 🔹 Contacts
                            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                                Text(
                                    text = "Контакты",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF2D2D2D)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                ContactItem(
                                    icon = R.drawable.mail_24dp_e3e3e3,
                                    value = email
                                )
                                ContactItem(
                                    icon = R.drawable.phone,
                                    value = phone
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GridItem(title: String, value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier.width(150.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF666666)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFF2D2D2D),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun PriceListItem(service: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = service,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF444444)
        )
        Text(
            text = price,
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF6A88F5),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ContactItem(icon: Int, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF6A88F5),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF444444)
        )
    }
}


@Composable
@Preview
fun prev(){
    val job = jobs.get(1)
    JobScreen()
}