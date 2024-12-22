package com.example.yurtok.presentation.screens.profile

sealed class ProfileEvent {
    data class OnNameChange(val name: String): ProfileEvent()
}