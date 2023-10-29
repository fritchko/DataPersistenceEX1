package com.example.datapersistenceex1.model.dto


import com.example.datapersistenceex1.model.domain.Fact
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class FactDTO(
    @SerializedName("fact")
    val fact: String?,
    @SerializedName("length")
    val length: Int?
)

fun Response<FactDTO>.toFact(): Fact?{
    val response = this.body()
    return if (this.isSuccessful){
        Fact(
            fact = response?.fact,
            length = response?.length
        )
    } else{
        null
    }
}