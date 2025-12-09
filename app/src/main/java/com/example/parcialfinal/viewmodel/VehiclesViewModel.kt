package com.example.parcialfinal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialfinal.data.model.Vehicle
import com.example.parcialfinal.data.repository.StarWarsRepository
import com.example.parcialfinal.utils.NetworkResult
import kotlinx.coroutines.launch

class VehiclesViewModel : ViewModel() {

    private val repository = StarWarsRepository()

    private val _vehiclesState = mutableStateOf<NetworkResult<List<Vehicle>>>(NetworkResult.Loading)
    val vehiclesState: State<NetworkResult<List<Vehicle>>> = _vehiclesState

    init {
        loadVehicles()
    }

    fun loadVehicles() {
        viewModelScope.launch {
            _vehiclesState.value = NetworkResult.Loading
            _vehiclesState.value = when (val result = repository.getVehicles()) {
                is NetworkResult.Success -> NetworkResult.Success(result.data.results)
                is NetworkResult.Error -> NetworkResult.Error(result.message)
                else -> NetworkResult.Error("Error desconocido")
            }
        }
    }
}