package com.example.yurtok.domain.useCase.applications

import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.domain.model.Application
import com.example.yurtok.domain.repository.ApplicationsRepository
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val applicationsRepository: ApplicationsRepository,
    private val tokenStorage: TokenStorage
) {

    suspend operator fun invoke(): List<Application>{
        val token = tokenStorage.getToken()
            ?: throw IllegalStateException("Пользователь не авторизован")
        return applicationsRepository.getApplications(token)
    }
}