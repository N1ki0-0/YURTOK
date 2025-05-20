package com.example.yurtok.di

import android.content.Context
import com.example.yurtok.data.local.SearchHistory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideSearchHistory(
        @ApplicationContext context: Context
    ): SearchHistory = SearchHistory(context)
}