package com.example.yurtok.domain.repository

import com.example.yurtok.domain.model.Application

interface ApplicationsRepository {
    suspend fun getApplications(token: String): List<Application>
    suspend fun applyToVacancy(token: String, vacancyId: Int, message: String?)
}