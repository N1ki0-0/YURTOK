package com.example.yurtok.domain.useCase.favorites

import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val tokenStorage: TokenStorage
){

    suspend operator fun invoke():List<Int>{
        val token = tokenStorage.getToken()
            ?: throw IllegalStateException("Пользователь не авторизован")
        return favoritesRepository.getFavoriteVacancyIds(token)
    }

}