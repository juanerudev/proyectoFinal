package com.example.parcialfinal.data.repository

import com.example.parcialfinal.data.model.*
import com.example.parcialfinal.data.remote.RetrofitClient
import com.example.parcialfinal.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StarWarsRepository {

    private val api = RetrofitClient.apiService

    suspend fun getPeople(): NetworkResult<PeopleResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPeople()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getPersonById(id: Int): NetworkResult<Person> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPersonById(id)
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getFilms(): NetworkResult<FilmsResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getFilms()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getFilmById(id: Int): NetworkResult<Film> = withContext(Dispatchers.IO) {
        try {
            val response = api.getFilmById(id)
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getPlanets(): NetworkResult<PlanetsResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPlanets()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getStarships(): NetworkResult<StarshipsResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getStarships()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getVehicles(): NetworkResult<VehiclesResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getVehicles()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun getPlanetById(id: Int): NetworkResult<Planet> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPlanetById(id)
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }
}