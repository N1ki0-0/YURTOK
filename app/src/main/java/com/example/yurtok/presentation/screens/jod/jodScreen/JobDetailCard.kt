package com.example.yurtok.presentation.screens.jod.jodScreen

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.yurtok.R
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.jod.jodCard.RatingStars

@Composable
fun JobDetailCard(
    vacancy: Vacancy,
    isFavorite: Boolean,
    onToggleFavorite:() -> Unit
){
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
                        painter = rememberImagePainter(data = "http://10.0.2.2:8080${vacancy.icon}"),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = vacancy.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF2D2D2D)
                    )
                    Text(
                        text = "${vacancy.serviceType} • ${vacancy.serviceSubType}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                    RatingStars(rating = vacancy.rating)
                }

                IconButton(onClick = onToggleFavorite)
                {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.bookmark_filled else R.drawable.bookmark
                        ),
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
                    text = vacancy.address,
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
                    "Опыт работы" to vacancy.experienceYears,
                    "Трудоустройство" to if (vacancy.needsEmployment) "Нужно" else "Не нужно",
                    "Консультация" to if (vacancy.freeConsultation) "Бесплатно" else "Платно",
                    "График" to vacancy.workDays
                )) { (title, value) ->
                    GridItem(title = title, value = value.toString())
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
                vacancy.priceList.forEach { (service, price) ->
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
                    text = vacancy.description,
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
                    value = vacancy.email
                )
                ContactItem(
                    icon = R.drawable.phone,
                    value = vacancy.phone
                )
            }
        }
    }
}