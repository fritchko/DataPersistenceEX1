package com.example.datapersistenceex1.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datapersistenceex1.model.domain.Fact
import com.example.datapersistenceex1.network.FactRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FactViewModel : ViewModel() {

    private val _result = MutableLiveData<Fact?>()
    val result: LiveData<Fact?>
        get() = _result

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getFacts() {
        viewModelScope.launch(IO) {
            _isLoading.postValue(true)
            val response = FactRepo.getFact()
            if (response != null) {
                _isLoading.postValue(false)
                _result.postValue(response)
                Log.i("DATA","$response")
            } else{
                Log.e("NETWORK","Couldn't achieve network call.")
            }
        }
    }

}