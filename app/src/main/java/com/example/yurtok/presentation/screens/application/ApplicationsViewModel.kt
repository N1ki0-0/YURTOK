package com.example.yurtok.presentation.screens.application

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.domain.model.Application
import com.example.yurtok.domain.repository.ApplicationsRepository
import com.example.yurtok.domain.useCase.applications.ApplyToVacancyUseCase
import com.example.yurtok.domain.useCase.applications.GetApplicationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationsViewModel @Inject constructor(
    private val getApplicationsUseCase: GetApplicationsUseCase,
    private val applyToVacancyUseCase: ApplyToVacancyUseCase
) : ViewModel() {

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications

    private val _appliedVacancyIds = MutableStateFlow<List<Int>>(emptyList())
    val appliedVacancyIds: StateFlow<List<Int>> = _appliedVacancyIds

    private val _uiState = MutableStateFlow(UiApplicationsState())
    val uiState: StateFlow<UiApplicationsState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiApplicationsEvent>(Channel.BUFFERED)
    val uiEvent: Flow<UiApplicationsEvent> = _uiEvent.receiveAsFlow()


    init {
        viewModelScope.launch {
            loadApplicationsInternal()
        }
    }

    private suspend fun loadApplicationsInternal(){
        _uiState.update { it.copy(isLoading = true, error = null) }
        try {
            val list = getApplicationsUseCase()
            _uiState.update { it.copy(applications = list, isAuth = true, isLoading = false) }
        }catch (e: Exception){
            val msg = e.localizedMessage ?: "Не удалось загрузить отклики"
            _uiState.update { it.copy(error = msg, applications = emptyList(), isLoading = false, isAuth = false) }
            _uiEvent.send(UiApplicationsEvent.ShowError(msg))
        }
    }

    fun loadApplications() {
        viewModelScope.launch {
            loadApplicationsInternal()
        }
    }

    fun applyToVacancy(vacancyId: Int, message: String?) {
        viewModelScope.launch {

            _uiState.update { it.copy(isApplying = true, error = null) }

            try{
                applyToVacancyUseCase(vacancyId, message)
                _appliedVacancyIds.update { it + vacancyId }
                loadApplicationsInternal()
            }catch (e: Exception){
                val msg = e.localizedMessage ?: "Не удалось откликнуться"
                _uiEvent.send(UiApplicationsEvent.ShowError(msg))
            } finally {
                _uiState.update { it.copy(isApplying = false) }
            }
        }
    }
}