package com.example.yurtok.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yurtok.domain.useCase.auth.GetCurrentUseCase
import com.example.yurtok.domain.useCase.auth.LogoutUseCase
import com.example.yurtok.presentation.state.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getCurrentUseCase: GetCurrentUseCase,
    private val logoutUseCase: LogoutUseCase
) :ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    init {
        _user.value = FirebaseAuth.getInstance().currentUser
    }

    fun logout() {
        logoutUseCase()
        FirebaseAuth.getInstance().signOut()
        _user.value = null
    }
}