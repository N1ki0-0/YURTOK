package com.example.yurtok.di

import com.example.yurtok.data.api.VacancyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

//@Module
//@InstallIn(SingletonComponent::class)
//class ServiceModule {
//    @Provides
//    fun provideCharacterService(retrofit: Retrofit): VacancyApi =
//        retrofit.create(VacancyApi::class.java)
//}