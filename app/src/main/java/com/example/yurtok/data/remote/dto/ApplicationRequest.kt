package com.example.yurtok.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationRequest(
    val vacancyId: Int,
    val message: String? = null
)