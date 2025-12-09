package com.example.parcialfinal.data.model

data class Film(
    val title: String,
    val episode_id: Int,
    val opening_crawl: String,
    val director: String,
    val producer: String,
    val release_date: String,
    val characters: List<String>,
    val planets: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
    val species: List<String>,
    val url: String
)

data class FilmsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Film>
)