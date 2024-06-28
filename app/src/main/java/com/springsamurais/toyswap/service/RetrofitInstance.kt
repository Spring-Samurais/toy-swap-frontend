package com.springsamurais.toyswap.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {

    private var retrofit: Retrofit? = null

    private val baseUrl: String = ""

    fun getService(): ListingAPIService {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ListingAPIService::class.java)
    }

}