package com.example.yurtok.domain.useCase.vacancies

import com.example.yurtok.domain.model.Vacancy
import com.example.yurtok.domain.repository.VacancyRepository
import javax.inject.Inject

class SearchVacanciesUseCase @Inject constructor(
    private val vacancyRepository: VacancyRepository
){
    suspend operator fun invoke(query: String):List<Vacancy>{
        return vacancyRepository.searchVacancies(query)
    }
}