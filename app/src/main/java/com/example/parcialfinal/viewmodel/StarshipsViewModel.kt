package com.example.parcialfinal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialfinal.data.model.Starship
import com.example.parcialfinal.data.repository.StarWarsRepository
import com.example.parcialfinal.utils.NetworkResult
import kotlinx.coroutines.launch

class StarshipsViewModel : ViewModel() {

    private val repository = StarWarsRepository()

    private val _starshipsState = mutableStateOf<NetworkResult<List<Starship>>>(NetworkResult.Loading)
    val starshipsState: State<NetworkResult<List<Starship>>> = _starshipsState

    init {
        loadStarships()
    }

    fun loadStarships() {
        viewModelScope.launch {
            _starshipsState.value = NetworkResult.Loading
            _starshipsState.value = when (val result = repository.getStarships()) {
                is NetworkResult.Success -> NetworkResult.Success(result.data.results)
                is NetworkResult.Error -> NetworkResult.Error(result.message)
                else -> NetworkResult.Error("Error desconocido")
            }
        }
    }
}