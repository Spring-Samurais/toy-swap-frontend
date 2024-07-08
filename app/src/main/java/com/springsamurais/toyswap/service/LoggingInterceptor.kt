package com.springsamurais.toyswap.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.Request
import okio.Buffer
import java.io.IOException

class LoggingInterceptor : Interceptor {
    companion object {
        private const val TAG = "LoggingInterceptor"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        Log.d(TAG, "Sending request: ${request.url} on ${chain.connection()} ${request.headers}")

        // Log the request body if it exists
        request.body?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            val requestBody = buffer.readUtf8()
            Log.d(TAG, "Request body: $requestBody")
        }

        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.d(TAG, "Received response for ${response.request.url} in ${(t2 - t1) / 1e6}ms ${response.headers}")

        return response
    }
}