package com.example.datapersistenceex1.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datapersistenceex1.model.domain.Fact
import com.example.datapersistenceex1.network.FactRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State

class FactViewModel : ViewModel() {

    private val _result = MutableStateFlow<Fact?>(null)
    val result: StateFlow<Fact?>
        get() = _result

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    fun getFacts() {
        viewModelScope.launch(IO) {
            _isLoading.value = true
            val response = FactRepo.getFact()
            if (response != null) {
                _isLoading.value = false
                _result.value = response
                Log.i("DATA","$response")
            } else{
                Log.e("NETWORK","Couldn't achieve network call.")
            }
        }
    }

}