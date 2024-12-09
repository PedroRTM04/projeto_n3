package com.example.projeton3

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MonitoringService : Service() {

    private val handler = Handler()
    private val interval = 60000L // Intervalo de 60 segundos
    private val client = OkHttpClient()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startMonitoring()
        return START_STICKY
    }

    private fun startMonitoring() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                monitorApiPerformance()
                handler.postDelayed(this, interval)
            }
        }, interval)
    }

    private fun monitorApiPerformance() {
        val request = Request.Builder()
            .url("https://api.unsplash.com/healthcheck")
            .build()

        val startTime = System.currentTimeMillis()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("MonitoringService", "API falhou: ${e.message}")
                sendNotification("Falha na API", "Detalhes: ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val endTime = System.currentTimeMillis()
                val responseTime = endTime - startTime

                if (response.isSuccessful) {
                    Log.d("MonitoringService", "API OK - Tempo de resposta: $responseTime ms")
                    if (responseTime > 2000) {
                        sendNotification("Alerta de lentidão", "Tempo de resposta: $responseTime ms")
                    }
                } else {
                    sendNotification("Erro na API", "Código HTTP: ${response.code}")
                }
            }
        })
    }


    private fun sendNotification(title: String, message: String) {
        Log.e("MonitoringService", "$title - $message")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
