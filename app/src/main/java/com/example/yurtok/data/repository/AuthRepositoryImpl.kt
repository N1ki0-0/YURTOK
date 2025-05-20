package com.example.yurtok.data.repository

import android.content.Context
import android.util.Log
import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.data.remote.api.AuthApi
import com.example.yurtok.data.remote.dto.LoginRequestDto
import com.example.yurtok.data.remote.dto.RegisterRequestDto
import com.example.yurtok.domain.mapper.toDomain
import com.example.yurtok.domain.model.RegisterData
import com.example.yurtok.domain.model.User
import com.example.yurtok.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    @ApplicationContext private val appContext: Context,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): User {
        val response = api.login(LoginRequestDto(email, password))
        Log.d("LOGIN_RESPONSE", "Server response: $response")
        response.data.authToken?.let {
            Log.d("TOKEN_SAVED", "Saving token: $it")
            tokenStorage.saveToken(it)
        }
        return response.data.toDomain()
    }

    override suspend fun register(data: RegisterData): User {
        // создаём текстовые части
        //fun String.toRequestBody() = toRequestBody("text/plain".toMediaType())

        val usernameRb = data.username.toRequestBody()
        val emailRb    = data.email.toRequestBody()
        val passRb     = data.password.toRequestBody()

        // 2) файл аватара, если есть
        val avatarPart: MultipartBody.Part? = data.avatar?.let { uri ->

            val cr = appContext.contentResolver
            val mime = cr.getType(uri)?.takeIf {
                it == "image/jpeg" || it == "image/png"
            } ?: throw IllegalArgumentException("Неподдерживаемый формат файла")
            val bytes = cr.openInputStream(uri)!!.use { it.readBytes() }
            val requestBody = bytes.toRequestBody(mime.toMediaType())
            // имя поля на сервере у тебя "avatar"
            MultipartBody.Part.createFormData(
                name = "avatar",
                filename = "avatar_${System.currentTimeMillis()}.${mime.substringAfter('/')}",
                body = requestBody
            )
        }

        // 3) вызываем Retrofit
        val response = api.register(
            username = usernameRb,
            email    = emailRb,
            password = passRb,
            avatar   = avatarPart
        )
        if (response.data == null) {
            throw Exception("Сервер не вернул пользователя (response.data is null)")
        }else{
            response.data.authToken?.let { tokenStorage.saveToken(it) }
            return response.data.toDomain()
        }

    }

    override suspend fun getProfile(): User {
        val response = api.getProfile()
        return response.data.toDomain()
    }
}