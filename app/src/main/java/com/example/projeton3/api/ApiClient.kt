package com.example.projeton3.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // URL base da API do Unsplash
    private const val BASE_URL = "https://api.unsplash.com/"

    // Criação do Retrofit, usando o padrão lazy para garantir que a instância seja criada apenas quando necessário
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Definindo a URL base
            .addConverterFactory(GsonConverterFactory.create()) // Usando o Gson para conversão de JSON para objetos Kotlin
            .build()
    }

    // Criação do serviço que irá fazer as requisições para a API do Unsplash
    val unsplashApiService: UnsplashApiService by lazy {
        retrofit.create(UnsplashApiService::class.java)
    }
}
