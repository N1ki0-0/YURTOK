package com.example.yurtok.data.repository

import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.data.remote.api.VacancyApi
import com.example.yurtok.data.remote.dto.ApplicationRequest
//import com.example.yurtok.data.remote.dto.FavoriteRequest
import com.example.yurtok.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val api: VacancyApi
) : FavoritesRepository {
    override suspend fun addFavorite(token: String, vacancyId: Int) {
        api.addFavorite("Bearer $token", vacancyId)
    }
    override suspend fun removeFavorite(token: String, vacancyId: Int) =
        api.removeFavorite("Bearer $token",vacancyId)

    override suspend fun getFavoriteVacancyIds(token: String): List<Int> {
        val response = api.getFavorites("Bearer $token")
        return response.favorites
    }

}