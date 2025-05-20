package com.example.yurtok.domain.repository

interface FavoritesRepository {
    suspend fun addFavorite(token: String, vacancyId: Int)
    suspend fun removeFavorite(token: String,vacancyId: Int)
    suspend fun getFavoriteVacancyIds(token: String): List<Int>
}