package com.example.projeton3.api

data class UnsplashResponse(
    val results: List<Result>
)

data class Result(
    val urls: Urls
)

data class Urls(
    val regular: String // Aqui você pode adicionar outros tipos de URLs, se necessário
)
