package com.example.parcialfinal.data.model

data class Planet(
    val name: String,
    val rotation_period: String,
    val orbital_period: String,
    val diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    val surface_water: String,
    val population: String,
    val residents: List<String>,
    val films: List<String>,
    val url: String
)

data class PlanetsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Planet>
)