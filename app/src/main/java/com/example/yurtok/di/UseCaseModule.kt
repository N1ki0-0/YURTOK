package com.example.yurtok.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
//    @Provides
//    @Singleton
//    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase{
//        return LoginUseCase(authRepository)
//    }
//
//    @Provides
//    @Singleton
//    fun provideSignupUseCase(authRepository: AuthRepository): SignupUseCase {
//        return SignupUseCase(authRepository)
//    }
//
//    @Provides
//    @Singleton
//    fun provideGetCurrentUseCase(authRepository: AuthRepository): GetCurrentUseCase {
//        return GetCurrentUseCase(authRepository)
//    }
//
//    @Provides
//    @Singleton
//    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
//        return LogoutUseCase(authRepository)
//    }
}