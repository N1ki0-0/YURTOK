package com.example.yurtok.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.domain.useCase.auth.SignupUseCase
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) :ViewModel(){

    //хранение состояния аунтификации пользователя при регистрации
    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    // Функция для регистрации нового пользователя с использованием электронной почты и пароля
    fun signup() = viewModelScope.launch {
        _signupFlow.value = Resource.Loading // Установка состояния загрузки перед выполнением регистрации
        val result = signupUseCase.invoke(name, email, password) // Вызов репозитория для выполнения регистрации
        _signupFlow.value = result // Обновление состояния регистрации с результатом
    }

    fun OnEvent(event: SignupEvent){
        when(event){
            is SignupEvent.OnNameChange -> name = event.name
            is SignupEvent.OnEmailChange -> email = event.email
            is SignupEvent.OnPasswordChange -> password = event.password
        }
    }

}