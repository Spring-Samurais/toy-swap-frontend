package com.springsamurais.toyswap.service

import android.util.Log
import com.springsamurais.toyswap.utils.LoggingInterceptor
import okhttp3.*
import java.io.IOException

object NetworkUtils {
    private const val TAG = "NetworkUtils"
    private const val BACKEND_URL = "http://10.0.2.2:8080/api/verifyToken"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    fun sendIdTokenToBackend(idToken: String?) {
        if (idToken == null) {
            Log.w(TAG, "ID token is null")
            return
        }

        val requestBody = FormBody.Builder()
            .add("idToken", idToken)
            .build()

        val request = Request.Builder()
            .url(BACKEND_URL)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $idToken") // Add the Authorization header
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed to send ID token to backend", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Successfully sent ID token to backend")
                    // Handle successful authentication
                } else {
                    val responseBody = response.body?.string()
                    Log.e(TAG, "Failed to authenticate with backend: $responseBody")
                }
            }
        })
    }
}