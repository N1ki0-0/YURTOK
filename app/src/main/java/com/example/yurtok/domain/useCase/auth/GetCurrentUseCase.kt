package com.example.yurtok.domain.useCase.auth

import com.example.yurtok.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class GetCurrentUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): FirebaseUser?{
        return authRepository.currentUser
    }
}