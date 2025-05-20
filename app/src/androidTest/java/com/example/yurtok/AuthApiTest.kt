package com.example.yurtok

import com.example.yurtok.data.remote.api.AuthApi
import com.example.yurtok.data.remote.dto.LoginRequestDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class AuthApiTest {

    private lateinit var retrofit: Retrofit
    private lateinit var authApi: AuthApi

    @Before
    fun setup() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Эмулятор = 10.0.2.2
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApi = retrofit.create(AuthApi::class.java)
    }

    @Test
    fun testLoginAndGetResponse() = runBlocking {
        val loginData = LoginRequestDto(
            email = "test225@test.com", // существующий пользователь
            password = "qwerty12345"   // его пароль
        )

        println("📤 Отправляем на сервер:")
        println(loginData)

        try {
            val response = authApi.login(loginData)

            println("📥 Получили от сервера:")
            println(response)

            assertNotNull(response)
            assertNotNull(response.data.authToken)
            assertEquals(loginData.email, response.data.email)

        } catch (e: Exception) {
            println("❗ Ошибка при авторизации: ${e.message}")
            fail("Login failed: ${e.message}")
        }
    }
}