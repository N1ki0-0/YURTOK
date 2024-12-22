package com.example.yurtok.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.R
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.login.LoginScreen

@Composable
fun ProfileScreen(navController: NavHostController,profileViewModel: ProfileViewModel = hiltViewModel()){

    val nameFlow by profileViewModel.user.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ){
        Row(modifier = Modifier.fillMaxWidth()){
            Image(painter = painterResource(id = R.drawable.cat_), contentDescription = "Photo",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop)
            Text(text = "Name ${nameFlow?.displayName ?: "Unknown"}",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold)

            IconButton(onClick = {profileViewModel.logout()
                navController.navigate(Route.LOGIN) {
                    popUpTo(0) { inclusive = true }
                }}) {
                Image(painter = painterResource(id = R.drawable.exit),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp))
            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun Gret(){
    val nav = rememberNavController()
    ProfileScreen(nav)
}