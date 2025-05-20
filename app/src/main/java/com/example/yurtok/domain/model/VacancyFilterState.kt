package com.example.yurtok.domain.model

data class VacancyFilterState(
    val query: String = "",
    val serviceType: String = "",
    val serviceSubType: String = "",
    val needsEmployment: Boolean? = null,
    val freeConsultation: Boolean? = null,
    val experienceRange: ClosedFloatingPointRange<Float> = 0f..30f,
    val rating: Float? = null,
    val location: String = ""
)