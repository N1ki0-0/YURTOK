package com.example.yurtok.domain.repository

import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String):Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String):Resource<FirebaseUser>
    fun logout()
}