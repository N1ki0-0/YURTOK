package com.example.yurtok.presentation.screens.application

import com.example.yurtok.domain.model.Application

data class UiApplicationsState(
    val isLoading: Boolean = false,
    val applications: List<Application> = emptyList(),
    val isAuth: Boolean = false,
    val error: String? = null,
    val isApplying: Boolean = false
)
