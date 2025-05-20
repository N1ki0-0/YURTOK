package com.example.yurtok.data.remote.api

import com.example.yurtok.data.remote.dto.AuthResponseDto
import com.example.yurtok.data.remote.dto.LoginRequestDto
import com.example.yurtok.data.remote.dto.RegisterRequestDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {
    @Multipart
    @POST("register")
    suspend fun register(
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part avatar: MultipartBody.Part? // может быть null
    ): AuthResponseDto

    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto

    @GET("user/me")
    suspend fun getProfile(): AuthResponseDto // Нужно ли передавать токен @Header("Authorization") token: String
}