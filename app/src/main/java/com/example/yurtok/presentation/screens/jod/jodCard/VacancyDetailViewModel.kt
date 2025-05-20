package com.example.yurtok.presentation.screens.jod.jodCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.domain.repository.VacancyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VacancyDetailViewModel @Inject constructor(
    private val repository: VacancyRepository
) : ViewModel() {

    private val _vacancy = MutableStateFlow<Vacancy?>(null)
    val vacancy: StateFlow<Vacancy?> = _vacancy

    fun loadVacancy(id: Int) {
        viewModelScope.launch {
            try {
                _vacancy.value = repository.getVacancyById(id)
            } catch (e: Exception) {
                _vacancy.value = null
            }
        }
    }
}