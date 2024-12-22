package com.example.yurtok.presentation.screens.login

sealed class LoginEvent {
    data class OnEmailChange(val email: String):LoginEvent()
    data class OnPasswordChange(val password: String):LoginEvent()
    //object Login: LoginEvent()
}