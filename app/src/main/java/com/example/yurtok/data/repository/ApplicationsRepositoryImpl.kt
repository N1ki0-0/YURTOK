package com.example.yurtok.data.repository

import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.data.remote.api.VacancyApi
import com.example.yurtok.data.remote.dto.ApplicationRequest
import com.example.yurtok.domain.model.Application
import com.example.yurtok.domain.repository.ApplicationsRepository
import javax.inject.Inject

class ApplicationsRepositoryImpl @Inject constructor(
    private val api: VacancyApi
) : ApplicationsRepository {

    override suspend fun getApplications(token: String): List<Application> {
        return api.getApplications("Bearer ${token}")
    }

    override suspend fun applyToVacancy(token: String, vacancyId: Int, message: String?) {
        api.applyToVacancy("Bearer ${token}", ApplicationRequest(vacancyId, message))
    }
}
