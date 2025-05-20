package com.example.yurtok.di

import com.example.yurtok.data.repository.ApplicationsRepositoryImpl
import com.example.yurtok.data.repository.AuthRepositoryImpl
import com.example.yurtok.data.repository.FavoritesRepositoryImpl
import com.example.yurtok.data.repository.VacancyRepositoryImpl
import com.example.yurtok.domain.repository.ApplicationsRepository
import com.example.yurtok.domain.repository.AuthRepository
import com.example.yurtok.domain.repository.FavoritesRepository
import com.example.yurtok.domain.repository.VacancyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindVacancyRepository(
        vacancyRepositoryImpl: VacancyRepositoryImpl
    ): VacancyRepository

    @Binds
    abstract fun bindApplicationsRepository(
        impl: ApplicationsRepositoryImpl
    ): ApplicationsRepository

    @Binds
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository
}