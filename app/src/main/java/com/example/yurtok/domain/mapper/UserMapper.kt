package com.example.yurtok.domain.mapper

import com.example.yurtok.data.remote.dto.UserDto
import com.example.yurtok.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        avatar = avatar,
        authToken = authToken
    )
}