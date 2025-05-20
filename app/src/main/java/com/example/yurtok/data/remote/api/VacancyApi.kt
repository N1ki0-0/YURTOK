package com.example.yurtok.data.remote.api

import com.example.yurtok.data.remote.dto.ApplicationRequest
import com.example.yurtok.domain.model.Application
import com.example.yurtok.domain.model.Favorites
import com.example.yurtok.domain.model.Vacancy
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VacancyApi {
    @GET("vacancies")
    suspend fun getVacancies(): List<Vacancy>

    @GET("vacancies/{id}")
    suspend fun getVacancyById(@Path("id") id: Int): Vacancy

    @POST("favorites/{vacancyId}")
    suspend fun addFavorite(
        @Header("Authorization") token: String,
        @Path("vacancyId") vacancyId: Int
    )

    @DELETE("favorites/{vacancyId}")
    suspend fun removeFavorite(
        @Header("Authorization") token: String,
        @Path("vacancyId") vacancyId: Int
    )

    @GET("favorites")
    suspend fun getFavorites(
        @Header("Authorization") token: String,
    ): Favorites

    @GET("applications")
    suspend fun getApplications(
        @Header("Authorization") token: String
    ): List<Application>

    @POST("applications")
    suspend fun applyToVacancy(
        @Header("Authorization") token: String,
        @Body request: ApplicationRequest
    )

    @GET("vacancies/search")
    suspend fun searchVacancies(
        @Query("query") query: String
    ):List<Vacancy>
}

//id
//photo
//name
//description
//contactInformation


//workExperience            : 10 years or more
//urgentOrders              : Yes
//guarantee                 : Yes
//workingDays               : Mon,Tue,Wed,Thu,Fri,Sat,Sun
//work                      : at home, at the client's place, remotely
//visiting                  : Yes
//freeConsultation          : Yes
//typeService               : Legal services