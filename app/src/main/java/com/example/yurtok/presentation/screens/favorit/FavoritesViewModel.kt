package com.example.yurtok.presentation.screens.favorit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.domain.repository.FavoritesRepository
import com.example.yurtok.domain.useCase.favorites.AddFavoriteUseCase
import com.example.yurtok.domain.useCase.favorites.GetFavoritesUseCase
import com.example.yurtok.domain.useCase.favorites.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavoritesUseCase,
    private val addFavorite: AddFavoriteUseCase,
    private val removeFavorite: RemoveFavoriteUseCase
) : ViewModel() {

    private val _favoriteVacancyIds = MutableStateFlow<List<Int>>(emptyList())
    val favoriteVacancyIds: StateFlow<List<Int>> = _favoriteVacancyIds

    private val _isAuthManager = MutableStateFlow(false)
    val isAuthManager: StateFlow<Boolean> = _isAuthManager

    private val _uiState = MutableStateFlow(UiFavoritesState())
    val uiState: StateFlow<UiFavoritesState> = _uiState.asStateFlow()

    private val _events = Channel<UiFavoritesEvent>(Channel.BUFFERED)
    val events: Flow<UiFavoritesEvent> = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadFavoritesInternal()
        }
    }

    private suspend fun loadFavoritesInternal(){
        _uiState.update { it.copy(isLoading = true, error = null) }
        try{
            val list = getFavorites()
            _uiState.update { it.copy(favorites = list, isAuth = true, isLoading = false) }
        }catch(e: Exception){
            val msg = e.localizedMessage ?: "Не удалось загрузить избранное"
            _uiState.update { it.copy(error = msg, favorites = emptyList(), isLoading = false, isAuth = false) }
            _events.send(UiFavoritesEvent.ShowError(msg))
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            loadFavoritesInternal()

        }
    }

    fun toggleFavorite(vacancyId: Int) {
        viewModelScope.launch {
            val currently = _uiState.value.favorites
            val isFav = vacancyId in currently
            _uiState.update { it.copy(
                favorites = if (isFav) currently - vacancyId else currently + vacancyId
            ) }

            try {
                if (isFav){
                    removeFavorite(vacancyId)
                }else{
                    addFavorite(vacancyId)
                }
                loadFavoritesInternal()
            }catch (e:Exception){
                val msg = e.localizedMessage ?: "Не удалось загрузить избранное"
                _events.send(UiFavoritesEvent.ShowError(msg))
                _uiState.update { it.copy(favorites = currently) }
            }

        }
    }
}