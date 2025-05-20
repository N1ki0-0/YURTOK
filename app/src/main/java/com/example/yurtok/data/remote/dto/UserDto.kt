package com.example.yurtok.data.remote.dto

import android.net.Uri

data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String?,
    val authToken: String?
)