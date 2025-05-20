package com.example.yurtok.domain.useCase.auth

import com.example.yurtok.domain.model.RegisterData
import com.example.yurtok.domain.model.User
import com.example.yurtok.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(data: RegisterData): User {
        return repository.register(data)
    }
}