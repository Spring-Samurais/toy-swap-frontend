package com.springsamurais.toyswap.utils

import android.util.Log
import okhttp3.*
import java.io.IOException

object NetworkUtils {
    private const val TAG = "NetworkUtils"
    private const val BACKEND_URL = "http://10.0.2.2:8080/api/verifyToken"

    fun sendIdTokenToBackend(idToken: String?) {
        if (idToken == null) {
            Log.w(TAG, "ID token is null")
            return
        }

        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("idToken", idToken)
            .build()

        val request = Request.Builder()
            .url(BACKEND_URL)
            .post(requestBody)
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
                    Log.e(TAG, "Failed to authenticate with backend: ${ response.message}")
                }
            }
        })
    }
}