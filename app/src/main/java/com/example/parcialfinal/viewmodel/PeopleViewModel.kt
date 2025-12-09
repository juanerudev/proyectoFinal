package com.example.parcialfinal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialfinal.data.model.Person
import com.example.parcialfinal.data.model.Planet
import com.example.parcialfinal.data.model.Film
import com.example.parcialfinal.data.repository.StarWarsRepository
import com.example.parcialfinal.utils.NetworkResult
import com.example.parcialfinal.utils.extractId
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {

    private val repository = StarWarsRepository()

    private val _peopleState = mutableStateOf<NetworkResult<List<Person>>>(NetworkResult.Loading)
    val peopleState: State<NetworkResult<List<Person>>> = _peopleState

    private val _personDetailState = mutableStateOf<NetworkResult<Person>>(NetworkResult.Loading)
    val personDetailState: State<NetworkResult<Person>> = _personDetailState

    private val _homeworldState = mutableStateOf<Planet?>(null)
    val homeworldState: State<Planet?> = _homeworldState

    private val _filmsState = mutableStateOf<List<Film>>(emptyList())
    val filmsState: State<List<Film>> = _filmsState

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadPeople()
    }

    fun loadPeople() {
        viewModelScope.launch {
            _peopleState.value = NetworkResult.Loading
            _peopleState.value = when (val result = repository.getPeople()) {
                is NetworkResult.Success -> NetworkResult.Success(result.data.results)
                is NetworkResult.Error -> NetworkResult.Error(result.message)
                else -> NetworkResult.Error("Error desconocido")
            }
        }
    }

    fun loadPersonDetail(id: Int) {
        viewModelScope.launch {
            _personDetailState.value = NetworkResult.Loading
            _homeworldState.value = null
            _filmsState.value = emptyList()

            when (val result = repository.getPersonById(id)) {
                is NetworkResult.Success -> {
                    _personDetailState.value = NetworkResult.Success(result.data)

                    // Cargar homeworld
                    result.data.homeworld.extractId()?.let { planetId ->
                        when (val planetResult = repository.getPlanetById(planetId)) {
                            is NetworkResult.Success -> {
                                _homeworldState.value = planetResult.data
                            }
                            else -> {}
                        }
                    }

                    // Cargar películas
                    val films = mutableListOf<Film>()
                    result.data.films.take(5).forEach { filmUrl ->
                        filmUrl.extractId()?.let { filmId ->
                            when (val filmResult = repository.getFilmById(filmId)) {
                                is NetworkResult.Success -> {
                                    films.add(filmResult.data)
                                }
                                else -> {}
                            }
                        }
                    }
                    _filmsState.value = films
                }
                is NetworkResult.Error -> {
                    _personDetailState.value = NetworkResult.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredPeople(): List<Person> {
        return when (val state = _peopleState.value) {
            is NetworkResult.Success -> {
                if (_searchQuery.value.isEmpty()) {
                    state.data
                } else {
                    state.data.filter {
                        it.name.contains(_searchQuery.value, ignoreCase = true)
                    }
                }
            }
            else -> emptyList()
        }
    }
}