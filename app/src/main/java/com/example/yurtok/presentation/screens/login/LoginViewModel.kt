package com.example.yurtok.presentation.screens.login

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.domain.model.User
import com.example.yurtok.domain.useCase.auth.LoginUserUseCase
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUserUseCase
): ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    private val _loginFlow = MutableStateFlow<Resource<User>?>(null)
    val loginFlow: StateFlow<Resource<User>?> = _loginFlow

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> email = event.email
            is LoginEvent.OnPasswordChange -> password = event.password
        }
    }

    fun login() {
        viewModelScope.launch {
            _loginFlow.value = Resource.Loading
            try {
                val user = loginUseCase(email, password)
                _loginFlow.value = Resource.Success(user)
            } catch (e: Exception) {
                _loginFlow.value = Resource.Failure(e)
            }
        }
    }

}