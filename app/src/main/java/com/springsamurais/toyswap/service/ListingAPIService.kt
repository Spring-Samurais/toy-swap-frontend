package com.springsamurais.toyswap.service

import com.springsamurais.toyswap.model.Listing
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ListingAPIService {

    @GET("listings")
    fun getListings(): Call<MutableList<Listing?>?>?

    @Multipart
    @POST("listings")
    fun postListing(
        @Part("title") title: RequestBody,
        @Part photo: MultipartBody.Part?,
        @Part("category") category: RequestBody,
        @Part("description") description: RequestBody,
        @Part("condition") condition: RequestBody,
        @Part("statusListing") statusListing: RequestBody
    ): Call<Listing>

    @PUT("listings/{id}")
    fun updateListing() // To be implemented

    @DELETE("listings/{id}")
    fun deleteListing(@Path("id") id: Long): Call<String>
}