package com.example.yurtok.domain.useCase.auth

import com.example.yurtok.domain.repository.AuthRepository
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser

class LoginUseCase (private val authRepository: AuthRepository) {
     suspend operator fun invoke(email: String, password: String): Resource<FirebaseUser>{
        return authRepository.login(email, password)
    }
}