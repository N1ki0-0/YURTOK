package com.example.yurtok.domain.useCase.vacancies

import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.domain.repository.VacancyRepository
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val repository: VacancyRepository
) {
    suspend operator fun invoke(): List<Vacancy> {
        return repository.getVacancies()
    }
}