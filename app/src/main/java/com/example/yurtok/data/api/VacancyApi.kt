package com.example.yurtok.data.api

import okhttp3.Response
import retrofit2.http.GET

interface VacancyApi {
    @GET("/api/products")
    suspend fun getVacancy():Response
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