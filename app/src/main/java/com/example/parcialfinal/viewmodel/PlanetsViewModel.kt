package com.example.parcialfinal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialfinal.data.model.Planet
import com.example.parcialfinal.data.repository.StarWarsRepository
import com.example.parcialfinal.utils.NetworkResult
import kotlinx.coroutines.launch

class PlanetsViewModel : ViewModel() {

    private val repository = StarWarsRepository()

    private val _planetsState = mutableStateOf<NetworkResult<List<Planet>>>(NetworkResult.Loading)
    val planetsState: State<NetworkResult<List<Planet>>> = _planetsState

    init {
        loadPlanets()
    }

    fun loadPlanets() {
        viewModelScope.launch {
            _planetsState.value = NetworkResult.Loading
            _planetsState.value = when (val result = repository.getPlanets()) {
                is NetworkResult.Success -> NetworkResult.Success(result.data.results)
                is NetworkResult.Error -> NetworkResult.Error(result.message)
                else -> NetworkResult.Error("Error desconocido")
            }
        }
    }
}