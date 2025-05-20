package com.example.yurtok.presentation.screens.signup

import android.net.Uri

sealed class SignupEvent {
    data class OnNameChange(val name: String): SignupEvent()
    data class OnEmailChange(val email: String): SignupEvent()
    data class OnPasswordChange(val password: String): SignupEvent()
    data class OnAvatarChange(val avatar: Uri?): SignupEvent()
}