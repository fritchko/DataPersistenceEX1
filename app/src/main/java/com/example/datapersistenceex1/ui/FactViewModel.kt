package com.example.datapersistenceex1.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datapersistenceex1.FactState
import com.example.datapersistenceex1.network.FactRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class FactViewModel : ViewModel() {

    private val _state = MutableSharedFlow<FactState>()
    val state: SharedFlow<FactState>
        get() = _state

    fun getFacts() {
        viewModelScope.launch(IO) {
            _state.emit(FactState.IsLoading)
            val fact = FactRepo.getFact()
            if (fact != null) {
                _state.emit(FactState.Result(
                    fact = fact
                ))
                Log.i("DATA","$fact")
            } else{
                Log.e("ERROR","Error in the view model.")
            }
        }
    }

}