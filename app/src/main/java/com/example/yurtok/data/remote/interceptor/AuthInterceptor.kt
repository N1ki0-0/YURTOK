package com.example.yurtok.data.remote.interceptor

import android.util.Log
import com.example.yurtok.data.local.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStorage.getToken()
        Log.d("AUTH_INTERCEPTOR", "Using token: $token")

        val request = if (!token.isNullOrEmpty()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}