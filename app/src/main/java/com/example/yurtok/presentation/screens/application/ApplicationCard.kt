package com.example.yurtok.presentation.screens.application

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yurtok.R
import com.example.yurtok.domain.model.Application

@Composable
fun ApplicationCard(application: Application, vacancyName: String) {
    var expanded by remember { mutableStateOf(false) }

    val statusColor = when (application.status.lowercase()) {
        "принято" -> Color(0xFF4CAF50)
        "отказано" -> Color(0xFFF44336)
        else -> Color(0xFF9E9E9E)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(), // плавное раскрытие
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.work_24dp_e3e3e3),
                    contentDescription = null,
                    tint = Color(0xFF6A88F5),
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = vacancyName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D2D2D)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Отправлено: ${application.createdAt.substring(0, 10)}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = application.status,
                        color = statusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Сообщение:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF424242)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = application.message ?: "Сообщение отсутствует",
                        fontSize = 14.sp,
                        color = Color(0xFF616161),
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}