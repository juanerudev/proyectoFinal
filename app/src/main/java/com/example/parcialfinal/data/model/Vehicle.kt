package com.example.parcialfinal.data.model

data class Vehicle(
    val name: String,
    val model: String,
    val manufacturer: String,
    val cost_in_credits: String,
    val length: String,
    val max_atmosphering_speed: String,
    val crew: String,
    val passengers: String,
    val cargo_capacity: String,
    val consumables: String,
    val vehicle_class: String,
    val pilots: List<String>,
    val films: List<String>,
    val url: String
)

data class VehiclesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Vehicle>
)