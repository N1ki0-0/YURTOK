package com.example.yurtok.domain.useCase.auth

import com.example.yurtok.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(){
        authRepository.logout()
    }
}