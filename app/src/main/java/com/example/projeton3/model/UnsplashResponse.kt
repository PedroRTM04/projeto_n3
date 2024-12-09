package com.example.projeton3.model

// Classe que representa a resposta da API do Unsplash
data class UnsplashResponse(
    val results: List<UnsplashPhoto> // Lista de fotos retornadas pela pesquisa
)

// Classe que representa cada foto retornada pela API
data class UnsplashPhoto(
    val id: String, // ID da foto
    val urls: PhotoUrls // URLs das imagens em diferentes resoluções
)

// Classe que representa as URLs das imagens
data class PhotoUrls(
    val full: String, // URL da imagem em alta resolução
    val regular: String, // URL da imagem em resolução regular
    val small: String // URL da imagem em baixa resolução
)
