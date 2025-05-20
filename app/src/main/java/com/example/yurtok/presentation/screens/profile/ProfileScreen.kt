package com.example.yurtok.presentation.screens.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.yurtok.R
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.login.LoginScreen

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val user by profileViewModel.user.collectAsState()
    val error by profileViewModel.error.collectAsState()

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // Profile Image with Shadow
            Box(
                modifier = Modifier
                    .shadow(16.dp, CircleShape, spotColor = Color(0x406A88F5))
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Image(
                    painter = rememberImagePainter(data = "http://10.0.2.2:8080${user?.avatar}"),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Name
            Text(
                text = user?.username ?: "Name",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Stats Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStatItem(value = "12", label = "Applications")
                ProfileStatItem(value = "4.7", label = "Rating")
                ProfileStatItem(value = "24", label = "Following")
            }

            // Profile Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = Color.White.copy(alpha = 0.9f),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileInfoItem(
                        icon = R.drawable.mail_24dp_e3e3e3,
                        title = "Email",
                        value = user?.email ?: "Not provided"
                    )

                    ProfileInfoItem(
                        icon = R.drawable.location_on_24dp_e3e3e3,
                        title = "Location",
                        value = "Moscow, Russia"
                    )

                    ProfileInfoItem(
                        icon = R.drawable.work_24dp_e3e3e3,
                        title = "Experience",
                        value = "5+ years"
                    )
                }
            }

            // Logout Button
            IconButton(
                onClick = {
                    profileViewModel.logout()
                    navController.navigate(Route.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.exit),
                    contentDescription = "Logout",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProfileInfoItem(icon: Int, title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color(0xFF6A88F5),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(
                text = value,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Gret(){
    val nav = rememberNavController()
    ProfileScreen(nav)
}