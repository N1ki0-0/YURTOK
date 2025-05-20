package com.example.yurtok.domain.useCase.vacancies

import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.domain.repository.VacancyRepository
import javax.inject.Inject

class GetVacancyByIdUseCase @Inject constructor(
    private val vacancyRepository: VacancyRepository
){

    suspend operator fun invoke(id: Int): Vacancy{
        return vacancyRepository.getVacancyById(id)
    }
}