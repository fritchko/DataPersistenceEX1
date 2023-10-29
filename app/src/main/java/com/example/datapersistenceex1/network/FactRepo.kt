package com.example.datapersistenceex1.network

import com.example.datapersistenceex1.model.domain.Fact
import com.example.datapersistenceex1.model.dto.toFact
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FactRepo {

    private var factService: FactService? = null

    suspend fun getFact(): Fact? {
        if (factService == null) {
            factService = provideRetrofit().create(FactService::class.java)
        }
        val response = factService?.getFact()

        return response?.toFact()
    }

    fun provideRetrofit(): Retrofit {

        val baseUrl = "https://catfact.ninja/"

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}