package com.example.projeton3

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.projeton3.api.ApiClient
import com.example.projeton3.api.UnsplashResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import android.graphics.drawable.BitmapDrawable

class MainActivity : AppCompatActivity() {

    private lateinit var apiStatusTextView: TextView
    private lateinit var startMonitoringButton: Button
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var imageView: ImageView
    private lateinit var downloadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialize os elementos da interface
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        imageView = findViewById(R.id.imageView)
        downloadButton = findViewById(R.id.downloadButton)
        apiStatusTextView = findViewById(R.id.apiStatusTextView) // Inicializa o TextView de status

        // Ao clicar no botão de pesquisa
        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                fetchImage(query)
            } else {
                Toast.makeText(this, "Digite uma palavra-chave para buscar uma imagem", Toast.LENGTH_SHORT).show()
            }
        }

        // Ao clicar no botão de download
        downloadButton.setOnClickListener {
            downloadImage()
        }

        // Inicia o monitoramento da API
        startMonitoringApi()
    }

    // Função para monitorar a API
    private fun startMonitoringApi() {
        val monitoringThread = Thread {
            while (true) {
                try {
                    // Simula uma verificação pingando a API
                    val response = ApiClient.unsplashApiService.testConnection("test", "hwbv00BC-SEccOaoyXicPwsKb7Ux4qM1XHvTJq8ciaY").execute()

                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Problema detectado na API: ${response.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                            apiStatusTextView.text = "Problema detectado: ${response.code()}"
                        }
                    } else {
                        runOnUiThread {
                            apiStatusTextView.text = "API funcionando corretamente"
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, "Falha ao acessar a API: ${e.message}", Toast.LENGTH_LONG).show()
                        apiStatusTextView.text = "Falha na API: ${e.message}"
                    }
                }

                // Espera 5 minutos antes de verificar novamente
                Thread.sleep(5 * 60 * 1000)
            }
        }
        monitoringThread.start()
    }


    // Função para buscar a imagem do Unsplash
    private fun fetchImage(query: String) {
        val apiKey = "hwbv00BC-SEccOaoyXicPwsKb7Ux4qM1XHvTJq8ciaY" // Sua chave de API

        // Chama o endpoint da API para buscar imagens
        ApiClient.unsplashApiService.searchImages(query, apiKey).enqueue(object : Callback<UnsplashResponse> {
            override fun onResponse(call: Call<UnsplashResponse>, response: Response<UnsplashResponse>) {
                if (response.isSuccessful) {
                    val unsplashResponse = response.body()
                    val imageUrl = unsplashResponse?.results?.firstOrNull()?.urls?.regular
                    imageUrl?.let {
                        // Usa o Glide para carregar a imagem na ImageView
                        Glide.with(this@MainActivity)
                            .load(it)
                            .into(imageView)

                        // Torna o botão de download visível após a imagem ser carregada
                        downloadButton.visibility = Button.VISIBLE
                    } ?: run {
                        Toast.makeText(this@MainActivity, "Nenhuma imagem encontrada.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Erro na busca de imagem.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UnsplashResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na conexão com a API.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Função para baixar e salvar a imagem
    private fun downloadImage() {
        // Verificar se a imagem foi carregada
        val drawable = imageView.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        if (bitmap != null) {
            saveImageToGallery(bitmap)
        } else {
            Toast.makeText(this, "Nenhuma imagem disponível para salvar.", Toast.LENGTH_SHORT).show()
        }
    }

    // Função para salvar a imagem na galeria do dispositivo
    private fun saveImageToGallery(bitmap: Bitmap) {
        try {
            val contentResolver = contentResolver

            // Definir os detalhes do arquivo de imagem
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "downloaded_image_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Unsplash") // Caminho na galeria (diretório "Pictures")
            }

            // Inserir a imagem no MediaStore
            val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            // Se a inserção foi bem-sucedida, salvar a imagem
            imageUri?.let { uri ->
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    Toast.makeText(this, "Imagem salva na galeria!", Toast.LENGTH_LONG).show()
                } ?: run {
                    Toast.makeText(this, "Falha ao abrir o stream de saída.", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "Falha ao salvar a imagem.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao salvar a imagem.", Toast.LENGTH_SHORT).show()
        }
    }
}
