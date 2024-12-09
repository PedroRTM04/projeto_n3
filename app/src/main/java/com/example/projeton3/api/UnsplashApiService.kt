package com.example.projeton3.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    // Método para buscar imagens
    @GET("search/photos")
    fun searchImages(
        @Query("query") query: String, // A palavra-chave para pesquisa
        @Query("client_id") apiKey: String // Chave da API do Unsplash
    ): Call<UnsplashResponse> // A resposta é um objeto do tipo UnsplashResponse

    // Método para testar a conexão com a API (usando o search/photos como teste)
    @GET("search/photos")
    fun testConnection(
        @Query("query") query: String, // Pesquisa uma palavra-chave qualquer
        @Query("client_id") apiKey: String // Chave da API do Unsplash
    ): Call<UnsplashResponse> // Apenas verifica se a API responde
}
