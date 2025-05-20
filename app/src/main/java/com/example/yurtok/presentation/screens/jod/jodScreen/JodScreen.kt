package com.example.yurtok.presentation.screens.jod.jodScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.application.ApplicationsViewModel
import com.example.yurtok.presentation.screens.application.UiApplicationsEvent
import com.example.yurtok.presentation.screens.favorit.FavoritesViewModel
import com.example.yurtok.presentation.screens.favorit.UiFavoritesEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun JobScreen(vacancy: Vacancy,
              navController: NavHostController,
              favoritesViewModel: FavoritesViewModel = hiltViewModel(),
              applicationsViewModel: ApplicationsViewModel = hiltViewModel()
) {

    val favState by favoritesViewModel.uiState.collectAsState()
    val appState by applicationsViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // ошибки избранного
        launch {
            favoritesViewModel.events.collect{ event ->
                if(event is UiFavoritesEvent.ShowError){
                    Toast.makeText(context, event.messang, Toast.LENGTH_SHORT).show()
                }
            }
        }
        // ошибки и (возможно добавлю успехи откликов)
        launch {
            applicationsViewModel.uiEvent.collect { event ->
                when (event) {
                    is UiApplicationsEvent.ShowError ->
                        Toast.makeText(context, event.messange, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // — Локальный стейт для диалога
    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val isAuth = favState.isAuth

    val isFavorite = vacancy.id in favState.favorites
    val isApplying = appState.isApplying
    val hasApplied = vacancy.id in appState.applications.map { it.vacancyId}
    //val applied = applicationsViewModel.appliedVacancyIds.collectAsState().value.contains(vacancy.id)


    val buttonText = if (hasApplied) "Вам скоро ответят" else "Связаться"
    val buttonColor by animateColorAsState(
        targetValue = if (hasApplied) Color(0xFF4CAF50) else Color(0xFF6A88F5),
        animationSpec = tween(durationMillis = 300)
    )

    if (showDialog) {
        AnimatedVisibility(
            visible = showDialog,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            applicationsViewModel.applyToVacancy(
                                vacancy.id,
                                message.ifBlank { null })
                            Toast.makeText(context, "Отклик успешно отправлен!", Toast.LENGTH_SHORT)
                                .show()
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A88F5)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Отправить", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = "Отмена", color = Color.Gray)
                    }
                },
                title = {
                    Text(
                        text = "Сопроводительное письмо",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF2D2D2D)
                    )
                },
                text = {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text("Введите сообщение...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }

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
                    onClick = {
                        if (isAuth && !hasApplied && !isApplying) {
                            showDialog = true
                        }
                    },
                    enabled = isAuth && !hasApplied && !isApplying,
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
                    JobDetailCard(
                        vacancy = vacancy,
                        isFavorite = isFavorite,
                        onToggleFavorite = {
                            if(isAuth) favoritesViewModel.toggleFavorite(vacancyId = vacancy.id)
                            else navController.navigate(Route.LOGIN){
                                navController.popBackStack(Route.SEARCH, inclusive = false)
                            }
                        }
                    )
                }
            }
        }
    }
}




@Composable
fun GridItem(title: String, value: String) {
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
internal fun PriceListItem(service: String, price: String) {
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
internal fun ContactItem(icon: Int, value: String) {
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




//@Composable
//@Preview
//fun prev(){
//    val job = jobs.get(1)
//    JobScreen()
//}