package com.example.yurtok.presentation.screens.favorit

data class UiFavoritesState(
    val isLoading: Boolean = false,
    val favorites: List<Int> = emptyList(),
    val isAuth: Boolean = false,
    val error: String? = null
)