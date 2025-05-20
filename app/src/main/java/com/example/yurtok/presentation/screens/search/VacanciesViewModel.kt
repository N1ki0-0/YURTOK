package com.example.yurtok.presentation.screens.search

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.R
import com.example.yurtok.data.local.SearchHistory
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.domain.model.VacancyFilterState
import com.example.yurtok.domain.useCase.vacancies.GetVacanciesUseCase
import com.example.yurtok.domain.useCase.vacancies.SearchVacanciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VacanciesViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val searchHistory: SearchHistory
) : ViewModel() {

    private val _vacancies = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacancies: StateFlow<List<Vacancy>> = _vacancies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistoryState = _searchHistory.asStateFlow()

    private val _uiState = MutableStateFlow(UiSearchState(
        query = savedStateHandle["query"] ?: ""
    ))
    val uiState: StateFlow<UiSearchState> = _uiState.asStateFlow()

    // 3️⃣ Состояние фильтров
    private val _filterState = MutableStateFlow(VacancyFilterState())
    val filterState: StateFlow<VacancyFilterState> = _filterState.asStateFlow()

    // Отфильтрованный список
    val filteredVacancies: StateFlow<List<Vacancy>> =
        combine(_vacancies, _filterState) { list, f ->
            list.filter { vac ->
                // текстовый поиск
                (f.query.isBlank()
                        || vac.name.contains(f.query, true)
                        || vac.serviceType.contains(f.query, true)
                        || vac.serviceSubType.contains(f.query, true))
                        // фильтр по serviceSubType (вся логика и для LazyRow, и для модалки)
                        && (f.serviceSubType.isBlank() || vac.serviceSubType == f.serviceSubType)
                        // стаж
                        && (vac.experienceYears in
                        f.experienceRange.start.toInt()..
                        f.experienceRange.endInclusive.toInt())
                        // рейтинг
                        && (f.rating == null || vac.rating >= f.rating)
                        // локация
                        && (f.location.isBlank() || vac.address.contains(f.location, true))
                        // трудоустройство
                        && (f.needsEmployment == null || vac.needsEmployment == f.needsEmployment)
                        && (f.serviceType.isBlank() || vac.serviceType == f.serviceType)
                        // бесплатная консультация
                        && (f.freeConsultation == null || vac.freeConsultation == f.freeConsultation)

            }
        }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _vacancies.value = try {
                getVacanciesUseCase()
            } catch (e: Exception) {
                emptyList()
            }.also { _isLoading.value = false }
        }
        loadVacancies()
        loadSearchHistory()
    }

    private fun loadSearchHistory() {
        _searchHistory.value = searchHistory.load()
    }

    fun addToHistory(query: String) {
        searchHistory.add(query)
        loadSearchHistory()
    }

    fun loadVacancies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getVacanciesUseCase()
                _vacancies.value = result
            } catch (e: Exception) {
                Log.e("Vacancies", "Ошибка загрузки", e)
                _vacancies.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        // Сохраняем в SavedStateHandle для восстановления при повороте
        savedStateHandle["query"] = newQuery
        // Обновляем состояние UI
        _uiState.update { it.copy(query = newQuery, error = null) }
    }

    fun clearQuery() {
        savedStateHandle["query"] = ""
        _uiState.update { it.copy(query = "") }
        setQuery("")
        cancelSearch()
    }

    fun performSearch() = viewModelScope.launch {
        val q = _uiState.value.query.trim()
        if (q.isNotEmpty()) {
            addToHistory(q)
        }
        _uiState.update { it.copy(items = filteredVacancies.value) }
        try {
            val res = searchVacanciesUseCase(q)
            _uiState.update { it.copy(isLoading = false, items = res, error = null) }
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, items = null, error = e.localizedMessage) }
        }
    }

    // Сброс результатов поиска, но оставляем query
    fun cancelSearch() {
        _uiState.update { it.copy(items = null, isLoading = false, error = null) }
    }

    fun retry() {
        performSearch()
    }

    // Методы для изменения фильтров:
    fun setQuery(q: String) =
        _filterState.update { it.copy(query = q) }

    fun setServiceType(subServ: String) =
        _filterState.update { it.copy(serviceType = subServ) }

    fun setServiceSubType(sub: String) =
        _filterState.update { it.copy(serviceSubType = sub) }

    fun setNeedsEmployment(needs: Boolean?) =
        _filterState.update { it.copy(needsEmployment = needs) }

    fun setFreeConsultation(free: Boolean?) =
        _filterState.update { it.copy(freeConsultation = free) }

    fun setExperienceRange(range: ClosedFloatingPointRange<Float>) =
        _filterState.update { it.copy(experienceRange = range) }

    fun setRating(r: Float?) =
        _filterState.update { it.copy(rating = r) }

    fun setLocation(loc: String) =
        _filterState.update { it.copy(location = loc) }

    fun resetFilters() {
        _filterState.value = VacancyFilterState()
    }
}