package com.example.yurtok.data.repository

import com.example.yurtok.data.remote.api.VacancyApi
import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.domain.repository.VacancyRepository
import javax.inject.Inject

class VacancyRepositoryImpl @Inject constructor(
    private val vacancyApi: VacancyApi
) : VacancyRepository {

    override suspend fun getVacancies(): List<Vacancy> {
        return vacancyApi.getVacancies()
    }

    override suspend fun getVacancyById(id: Int): Vacancy {
        return vacancyApi.getVacancyById(id)
    }

    override suspend fun searchVacancies(query: String): List<Vacancy> {
        return vacancyApi.searchVacancies(query)
    }
}