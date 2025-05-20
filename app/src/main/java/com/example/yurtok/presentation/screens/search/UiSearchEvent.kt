package com.example.yurtok.presentation.screens.search

import com.example.yurtok.domain.model.Vacancy

data class UiSearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val items: List<Vacancy>? = null,
    val error: String? = null
)
