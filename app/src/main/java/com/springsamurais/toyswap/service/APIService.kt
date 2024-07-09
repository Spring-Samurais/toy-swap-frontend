package com.springsamurais.toyswap.service

import com.springsamurais.toyswap.model.Comment
import com.springsamurais.toyswap.model.CommentRequest
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.ui.login.data.LoginRequest
import com.springsamurais.toyswap.ui.login.data.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("listings")
    fun getListings() : Call<List<Listing>>

    @GET("comments/listing/{id}")
    fun getCommentsByListing(@Path("id") listingId: Long) : Call<List<Comment>>

    @Multipart
    @POST("/api/v1/listings")
    fun postListing(
        @Part("title") title: RequestBody,
        @Part("userID") userid: RequestBody,
        @Part("category") category: RequestBody,
        @Part("description") description: RequestBody,
        @Part("condition") condition: RequestBody,
        @Part("statusListing") statusListing: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Call<Listing>

    @POST("members/login")
    fun login(@Body loginRequest: LoginRequest): Call<Member>

    @POST("members/register")
    fun register(@Body member: Member): Call<Member>

    @POST("comments")
    fun postComment(@Body comment: CommentRequest): Call<Comment>

    @PUT("listings/{id}")
    fun updateListing() // To be implemented

    @DELETE("listings/{id}")
    fun deleteListing(@Path("id") id: Long): Call<String>
}