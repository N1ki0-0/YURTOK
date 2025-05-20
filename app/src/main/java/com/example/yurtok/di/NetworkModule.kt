package com.example.yurtok.di

import android.content.Context
import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.data.remote.api.AuthApi
import com.example.yurtok.data.remote.api.VacancyApi
import com.example.yurtok.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideTokenStorage(@ApplicationContext context: Context): TokenStorage =
        TokenStorage(context)

    @Provides
    fun provideAuthInterceptor(tokenStorage: TokenStorage): AuthInterceptor =
        AuthInterceptor(tokenStorage)

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun provideVacancyApi(retrofit: Retrofit): VacancyApi =
        retrofit.create(VacancyApi::class.java)

//    @Provides
//    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY // Полная детализация
//        return interceptor
//    }
//
//    @Provides
//    fun provideOkHttpClient(
//        authInterceptor: AuthInterceptor,
//        loggingInterceptor: HttpLoggingInterceptor
//    ): OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(authInterceptor) // наш токен
//        .addInterceptor(loggingInterceptor) // логирование всех запросов
//        .build()
}