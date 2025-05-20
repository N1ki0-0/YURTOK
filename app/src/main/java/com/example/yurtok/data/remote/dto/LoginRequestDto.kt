package com.example.yurtok.data.remote.dto

import kotlinx.serialization.Serializable


data class LoginRequestDto(
    val email: String,
    val password: String
)
