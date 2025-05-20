package com.example.yurtok.domain.model

import android.net.Uri

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val avatar: String?,
    val authToken: String?
)