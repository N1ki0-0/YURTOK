package com.example.yurtok.presentation.screens.application

sealed class UiApplicationsEvent {
    data class ShowError(val messange: String): UiApplicationsEvent()
}