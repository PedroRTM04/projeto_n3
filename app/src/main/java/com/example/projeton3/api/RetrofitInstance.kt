package com.example.projeton3.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.unsplash.com/"

    // Instância do Retrofit que será usada em toda a aplicação
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Define a URL base da API
            .addConverterFactory(GsonConverterFactory.create()) // Usando Gson para converter JSON em objetos
            .build()
    }
}
