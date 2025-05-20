package com.example.yurtok.domain.useCase.applications

import com.example.yurtok.data.local.TokenStorage
import com.example.yurtok.domain.repository.ApplicationsRepository
import javax.inject.Inject

class ApplyToVacancyUseCase @Inject constructor(
    private val applicationsRepository: ApplicationsRepository,
    private val tokenStorage: TokenStorage
) {

    suspend operator fun invoke(vacancyId: Int, message: String?){
        val token = tokenStorage.getToken()
            ?: throw IllegalStateException("Пользователь не авторизован")
        applicationsRepository.applyToVacancy(token, vacancyId, message)
    }

}