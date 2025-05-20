package com.example.yurtok.domain.useCase.auth

import com.example.yurtok.domain.model.User
import com.example.yurtok.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): User {
        return repository.login(email, password)
    }
}