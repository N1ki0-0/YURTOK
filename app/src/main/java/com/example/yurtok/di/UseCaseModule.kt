package com.example.yurtok.di

import com.example.yurtok.domain.repository.AuthRepository
import com.example.yurtok.domain.useCase.auth.GetCurrentUseCase
import com.example.yurtok.domain.useCase.auth.LoginUseCase
import com.example.yurtok.domain.useCase.auth.LogoutUseCase
import com.example.yurtok.domain.useCase.auth.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase{
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignupUseCase(authRepository: AuthRepository): SignupUseCase {
        return SignupUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUseCase(authRepository: AuthRepository): GetCurrentUseCase {
        return GetCurrentUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }
}