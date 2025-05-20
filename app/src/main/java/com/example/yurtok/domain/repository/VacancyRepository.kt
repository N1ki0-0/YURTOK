package com.example.yurtok.domain.repository

import com.example.yurtok.domain.model.Vacancy

interface VacancyRepository {
    suspend fun getVacancies(): List<Vacancy>
    suspend fun getVacancyById(id: Int): Vacancy
    suspend fun searchVacancies(query: String): List<Vacancy>
}