package com.springsamurais.toyswap.service

import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.ui.login.data.LoginRequest
import com.springsamurais.toyswap.ui.login.data.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("listings")
    fun getListings(): Call<List<Listing>>

    @Multipart
    @POST("/api/v1/listings")
    fun postListing(
        @Part("title") title: RequestBody,
        @Part photo: MultipartBody.Part?,
        @Part("category") category: RequestBody,
        @Part("description") description: RequestBody,
        @Part("condition") condition: RequestBody,
        @Part("statusListing") statusListing: RequestBody,
        @Part("userid") userid: RequestBody
    ): Call<Listing>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @PUT("listings/{id}")
    fun updateListing() // To be implemented

    @DELETE("listings/{id}")
    fun deleteListing(@Path("id") id: Long): Call<String>
}