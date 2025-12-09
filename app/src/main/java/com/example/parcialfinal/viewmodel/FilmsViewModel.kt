package com.example.parcialfinal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialfinal.data.model.Film
import com.example.parcialfinal.data.repository.StarWarsRepository
import com.example.parcialfinal.utils.NetworkResult
import kotlinx.coroutines.launch

class FilmsViewModel : ViewModel() {

    private val repository = StarWarsRepository()

    private val _filmsState = mutableStateOf<NetworkResult<List<Film>>>(NetworkResult.Loading)
    val filmsState: State<NetworkResult<List<Film>>> = _filmsState

    private val _filmDetailState = mutableStateOf<NetworkResult<Film>>(NetworkResult.Loading)
    val filmDetailState: State<NetworkResult<Film>> = _filmDetailState

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            _filmsState.value = NetworkResult.Loading
            _filmsState.value = when (val result = repository.getFilms()) {
                is NetworkResult.Success -> NetworkResult.Success(
                    result.data.results.sortedBy { it.episode_id }
                )
                is NetworkResult.Error -> NetworkResult.Error(result.message)
                else -> NetworkResult.Error("Error desconocido")
            }
        }
    }

    fun loadFilmDetail(id: Int) {
        viewModelScope.launch {
            _filmDetailState.value = NetworkResult.Loading
            _filmDetailState.value = repository.getFilmById(id)
        }
    }
}