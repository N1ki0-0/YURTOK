package com.example.yurtok.presentation.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yurtok.R
import com.example.yurtok.presentation.screens.filter.FilterScreen
import com.example.yurtok.presentation.screens.filter.fan
import com.example.yurtok.presentation.screens.jod.jodCard.JobCard
import com.example.yurtok.presentation.screens.jod.jodCard.jobs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val category = LocalContext.current.resources.getStringArray(R.array.category)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF6A88F5),
                    Color(0xFFA478E8)
                )
            )
        ),
        verticalArrangement = Arrangement.Top
        )
    {
        SearchBar(
            modifier = Modifier.padding( horizontal = if (active) 0.dp else 16.dp),
            query = query, //Текущий текст
            onQueryChange = {query = it},
            onSearch = {active = false},
            active = active,
            onActiveChange = {active = it},
            placeholder = { Text("Введите запрос") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.search_bar), contentDescription = "") },
            trailingIcon = { IconButton(onClick = {
                //Кнопка
            }) {

                Icon(painter = painterResource(id = R.drawable.settings), contentDescription = "")
            } },
            colors = SearchBarDefaults.colors(Color.White),

        ) {
            // Список результатов поиска (подсказки)
            val suggestions = listOf("Kotlin", "Jetpack Compose", "Material 3", "SearchBar")
                .filter { it.contains(query, ignoreCase = true) }

            suggestions.forEach { suggestion ->
                ListItem(
                    headlineContent = { Text(suggestion) },
                    modifier = Modifier
                        .clickable {
                            query = suggestion
                            active = false
                        }
                        .padding(8.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //val item = listOf("Kotlin", "Jetpack Compose", "Material 3", "SearchBar", "a", "asdsaf")
            var selectedIndex by remember { mutableStateOf(-1) }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(category) { index, text ->
                    val isSelected = selectedIndex == index

                    TextButton(
                        onClick = {
                            selectedIndex = if (isSelected) -1 else index
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = if (isSelected) colorResource(id = R.color.teal_200) else Color.Transparent,
                            contentColor = if (isSelected) Color.White else colorResource(id = R.color.white)
                        ),
                        shape = RoundedCornerShape(24.dp),

                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = text,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.padding( horizontal = 16.dp))
        {

            LazyColumn {
                val modifier = Modifier.fillMaxWidth().padding(8.dp)
                items(jobs) { job ->
                    JobCard(job, modifier)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    val nav = rememberNavController()
    SearchScreen(nav)

}