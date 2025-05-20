package com.example.yurtok.data.local

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorage @Inject constructor(
    @ApplicationContext context: Context
){
    private val dataStore = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        Log.d("TOKEN_STORAGE", "Token saved: $token")
        dataStore.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        val token = dataStore.getString("auth_token", null)
        Log.d("TOKEN_STORAGE", "Token loaded: $token")
        return token
    }

    fun clearToken() {
        dataStore.edit().clear().apply()
    }
}