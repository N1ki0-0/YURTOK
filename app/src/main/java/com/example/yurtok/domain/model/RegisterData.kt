package com.example.yurtok.domain.model

import android.net.Uri

data class RegisterData(
    val username: String,
    val email: String,
    val password: String,
    val avatar: Uri?
)