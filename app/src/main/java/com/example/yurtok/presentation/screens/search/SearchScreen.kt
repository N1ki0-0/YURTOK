package com.example.yurtok.presentation.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.yurtok.R
import com.example.yurtok.data.local.SearchHistory
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.filter.FilterScreen
import com.example.yurtok.presentation.screens.jod.jodCard.JobCard
import com.example.yurtok.presentation.screens.jod.jodCard.ShimmerJobCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController,
                 viewModel: VacanciesViewModel = hiltViewModel()) {

    var query by remember { mutableStateOf("") }

    // — Стейт LazyRow-категорий
    val categories = LocalContext.current.resources.getStringArray(R.array.category)
    val filter by viewModel.filterState.collectAsState()

    // 2) Стейт фильтров
    var showFilter by remember { mutableStateOf(false) }
    val needsEmployment = filter.needsEmployment
    val selectedService = filter.serviceType
    val freeConsultation = filter.freeConsultation
    val experienceRange = filter.experienceRange
    val rating = filter.rating
    val location = filter.location

    val vacancies by viewModel.filteredVacancies.collectAsState()
    val filtered by viewModel.filteredVacancies.collectAsState()
    val searchHistory by viewModel.searchHistoryState.collectAsState()

    val state by viewModel.uiState.collectAsState()
    var active by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    BackHandler(enabled = active || state.query.isNotEmpty()) {
        when {
            active -> active = false
            state.query.isNotEmpty() -> {
                viewModel.clearQuery()
                viewModel.cancelSearch()
            }
        }
    }

    LaunchedEffect(active) {
        if (active) {
            focusRequester.requestFocus()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadVacancies()
    }

    Box(Modifier.fillMaxSize()) {

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (active) Modifier.padding(horizontal = 0.dp)
                        else Modifier.padding(horizontal = 16.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val sampleSuggestions = listOf("Android", "iOS", "Web", "Kotlin", "Compose")
                    .filter { it.contains(state.query, ignoreCase = true) }

                // SearchBar тянется на всё свободное место
                SearchBar(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .focusable(),
                    query = state.query,
                    onQueryChange = { viewModel.onQueryChange(it) },
                    onSearch = {
                        keyboardController?.hide()
                        viewModel.performSearch()
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text("Введите запрос") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_bar),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (state.query.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.clearQuery()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close),
                                    contentDescription = "Очистить"
                                )
                            }
                        }
                    },
                    colors = SearchBarDefaults.colors(containerColor = Color.White),
                    tonalElevation = 0.dp,

                ) {
                    if (active) {
                        val suggestions = if (state.query.isEmpty()) {
                            searchHistory
                        } else {
                            searchHistory.filter { it.contains(state.query, true) }
                        }

                        LazyColumn {
                            if (suggestions.isEmpty()) {
                                item {
                                    Text(
                                        text = if (state.query.isEmpty()) "История поиска пуста" else "Совпадений не найдено",
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            } else {
                                items(suggestions) { suggestion ->
                                    ListItem(
                                        modifier = Modifier.clickable {
                                            viewModel.onQueryChange(suggestion)
                                            viewModel.performSearch()
                                            active = false
                                        },
                                        headlineContent = { Text(suggestion) }
                                    )
                                }
                            }
                        }
                    }
                }


                if(!active){

                    Spacer(Modifier.width(8.dp))
                    // Кнопка фильтра справа
                    IconButton(
                        modifier = Modifier.padding(top = 32.dp),
                        onClick = { showFilter = true }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = "Фильтр",

                            )
                    }
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var selectedIndex by remember { mutableStateOf(-1) }
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(categories) { index, text ->
                        val isSelected = filter.serviceSubType == text
                        TextButton(
                            onClick = { viewModel.setServiceSubType(if (isSelected) "" else text) },
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

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if(selectedService.isNotBlank()) FilterChip(
                    selected = true,
                    onClick = {viewModel.setServiceType("")},
                    label = {
                        Text(filter.serviceType)
                    }
                )


                needsEmployment?.let {
                    FilterChip(
                        selected = true,
                        onClick = { viewModel.setNeedsEmployment(null) },
                        label = { Text(if (it) "Нужен трудоустр." else "Не нужен") }
                    )
                }
                freeConsultation?.let {
                    FilterChip(
                        selected = true,
                        onClick = { viewModel.setFreeConsultation(null) },
                        label = { Text(if (it) "Бесплатно" else "Платно") }
                    )
                }
                if (experienceRange != 0f..30f) {
                    FilterChip(
                        selected = true,
                        onClick = { viewModel.setExperienceRange(0f..30f) },
                        label = {
                            val r = experienceRange
                            Text("${r.start.toInt()}–${r.endInclusive.toInt()} лет")
                        }
                    )
                }
                rating?.let { r ->
                    FilterChip(
                        selected = true,
                        onClick = { viewModel.setRating(null) },
                        label = { Text("${r.toInt()}★") }
                    )
                }
                if (location.isNotBlank()) {
                    FilterChip(
                        selected = true,
                        onClick = { viewModel.setLocation("") },
                        label = { Text(location) }
                    )
                }
            }

            Column(modifier = Modifier.padding( horizontal = 16.dp))
            {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    if(isLoading){
                        items(5){ ShimmerJobCard(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }else{

                        val results = state.items
                        if (results != null) {
                            // Была попытка поиска (и она либо дала список, либо пустой)
                            if (results.isEmpty()) {
                                item {
                                    Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                        Text("Ничего не найдено.")
                                    }
                                }
                            } else {
                                items(results) { vacancy ->
                                    JobCard(
                                        vacancy = vacancy,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate("vacancy/${vacancy.id}")
                                            }
                                            .padding(8.dp),
                                        navController = navController
                                    )
                                }
                            }
                        } else {
                            // Поиск ещё не выполняли — показываем список по фильтрам
                            items(filtered) { vacancy ->
                                JobCard(
                                    vacancy = vacancy,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate("vacancy/${vacancy.id}")
                                        }
                                        .padding(8.dp),
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showFilter) {
            FilterScreen(
                navController = navController,
                selectedService = filter.serviceType,
                onServiceChange = { viewModel.setServiceType(it)
                },
                needsEmployment = filter.needsEmployment,
                onNeedsEmploymentChange = { viewModel.setNeedsEmployment(it) },
                freeConsultation = filter.freeConsultation,
                onFreeConsultationChange = { viewModel.setFreeConsultation(it) },
                experienceRange = filter.experienceRange,
                onExperienceChange = { viewModel.setExperienceRange(it) },
                rating = filter.rating,
                onRatingChange = { viewModel.setRating(it) },
                location = filter.location,
                onLocationChange = { viewModel.setLocation(it) },
                onReset = { viewModel.resetFilters() },
                onSubmit = { showFilter = false }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewSearchScreen() {
//    val nav = rememberNavController()
//    SearchScreen(nav)
//
//}