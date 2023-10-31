package com.example.datapersistenceex1

import com.example.datapersistenceex1.model.domain.Fact
import java.lang.Exception

sealed class FactState {

    data object IsLoading : FactState()

    data class Result(val fact: Fact) : FactState()

    data class Error(val error: Exception)

}