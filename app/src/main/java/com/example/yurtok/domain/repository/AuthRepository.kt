package com.example.yurtok.domain.repository

import com.example.yurtok.domain.model.RegisterData
import com.example.yurtok.domain.model.User
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(data: RegisterData): User
    suspend fun getProfile(): User
}