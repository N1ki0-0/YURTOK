package com.example.yurtok.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.domain.model.User
import com.example.yurtok.domain.useCase.auth.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val tokenStorage: TokenStorage
) :ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadProfile()
    }

    private fun loadProfile(){
        viewModelScope.launch {
            try {
                val user = getProfileUseCase()
                Log.d("PROFILE_RESPONSE", "Loaded user: $user")
                _user.value = user
            }catch (e: Exception){
                Log.e("PROFILE_ERROR", "Error loading profile: ${e.message}")
                _error.value = e.message
            }
        }
    }

    fun logout(){
        tokenStorage.clearToken()
        _user.value = null
    }
}