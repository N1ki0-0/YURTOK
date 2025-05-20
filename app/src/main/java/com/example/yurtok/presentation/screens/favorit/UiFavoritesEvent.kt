package com.example.yurtok.presentation.screens.favorit

sealed class UiFavoritesEvent {
    data class ShowError(val messang: String): UiFavoritesEvent()

}