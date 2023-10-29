package com.example.datapersistenceex1.network

import com.example.datapersistenceex1.model.dto.FactDTO
import retrofit2.Response
import retrofit2.http.GET

interface FactService {

    @GET("fact")
    suspend fun getFact(): Response<FactDTO>

}