package com.example.yurtok.presentation.screens.signup

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.domain.model.RegisterData
import com.example.yurtok.domain.model.User
import com.example.yurtok.domain.useCase.auth.RegisterUserUseCase
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) :ViewModel(){

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var avatar by mutableStateOf<Uri?>(null)
        private set

    private val _registerState = MutableStateFlow<Resource<User>?>(null)
    val registerState: StateFlow<Resource<User>?> = _registerState

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnNameChange -> name = event.name
            is SignupEvent.OnEmailChange -> email = event.email
            is SignupEvent.OnPasswordChange -> password = event.password
            is SignupEvent.OnAvatarChange -> avatar = event.avatar
        }
    }

    fun signup() {
        // Валидация
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registerState.value = Resource.Failure(Exception("Все поля обязательны"))
            return
        }

        viewModelScope.launch {
            _registerState.value = Resource.Loading
            try {
                val user = registerUserUseCase(
                    RegisterData(
                        username  = name,
                        email     = email,
                        password  = password,
                        avatar = avatar  // передаём Uri?
                    )
                )
                _registerState.value = Resource.Success(user)
            } catch (e: Exception) {
                _registerState.value = Resource.Failure(e)
            }
        }
    }

}