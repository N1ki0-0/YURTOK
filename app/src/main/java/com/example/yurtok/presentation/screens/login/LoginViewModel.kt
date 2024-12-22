package com.example.yurtok.presentation.screens.login

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.domain.useCase.auth.LoginUseCase
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    //хранение состояния аунтификации пользователя при входе
    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun login() = viewModelScope.launch{
        _loginFlow.value = Resource.Loading
        val result = loginUseCase.invoke(email, password)
        _loginFlow.value = result
    }

    fun OnEvent(event: LoginEvent){
        when(event){
            is LoginEvent.OnEmailChange -> email = event.email
            is LoginEvent.OnPasswordChange -> password = event.password
        }
    }

}