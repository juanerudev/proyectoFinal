package com.example.parcialfinal.data.remote

import com.example.parcialfinal.data.model.*
import com.example.parcialfinal.data.model.Film
import com.example.parcialfinal.data.model.FilmsResponse
import com.example.parcialfinal.data.model.PeopleResponse
import com.example.parcialfinal.data.model.Person
import com.example.parcialfinal.data.model.Planet
import com.example.parcialfinal.data.model.PlanetsResponse
import com.example.parcialfinal.data.model.Starship
import com.example.parcialfinal.data.model.StarshipsResponse
import com.example.parcialfinal.data.model.Vehicle
import com.example.parcialfinal.data.model.VehiclesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StarWarsApiService {

    @GET("people/")
    suspend fun getPeople(): PeopleResponse

    @GET("people/{id}/")
    suspend fun getPersonById(@Path("id") id: Int): Person

    @GET("films/")
    suspend fun getFilms(): FilmsResponse

    @GET("films/{id}/")
    suspend fun getFilmById(@Path("id") id: Int): Film

    @GET("planets/")
    suspend fun getPlanets(): PlanetsResponse

    @GET("planets/{id}/")
    suspend fun getPlanetById(@Path("id") id: Int): Planet

    @GET("starships/")
    suspend fun getStarships(): StarshipsResponse

    @GET("starships/{id}/")
    suspend fun getStarshipById(@Path("id") id: Int): Starship

    @GET("vehicles/")
    suspend fun getVehicles(): VehiclesResponse

    @GET("vehicles/{id}/")
    suspend fun getVehicleById(@Path("id") id: Int): Vehicle
}