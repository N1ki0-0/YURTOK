package com.example.yurtok.presentation.screens.favorit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.R
import com.example.yurtok.presentation.screens.jod.jodCard.JobCard
import com.example.yurtok.presentation.screens.jod.jodCard.JobCardRow
import com.example.yurtok.presentation.screens.jod.jodCard.jobs


@Composable
fun FavoritesScreen(navController: NavHostController) {
    val resource = LocalContext.current.resources
    var favoritesB by remember { mutableStateOf(false) }
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
        Text(text = "${resource.getText(R.string.favorites)}",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
            )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "0 вакансий",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
        if(favoritesB){

        }else{
            TextWithIcon()
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




@Preview(showBackground = true)
@Composable
fun PreviewJobDetailsScreen() {
    val nav = rememberNavController()
    FavoritesScreen(nav)

}