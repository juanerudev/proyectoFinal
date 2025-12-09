package com.example.parcialfinal.data.model

data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val url: String
)

data class PeopleResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Person>
)