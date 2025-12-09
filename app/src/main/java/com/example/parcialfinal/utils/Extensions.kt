package com.example.parcialfinal.utils

fun String.extractId(): Int? {
    val regex = """/(\d+)/$""".toRegex()
    return regex.find(this)?.groupValues?.get(1)?.toIntOrNull()
}