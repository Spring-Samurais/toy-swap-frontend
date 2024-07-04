package com.springsamurais.toyswap.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.springsamurais.toyswap.service.APIService
import com.springsamurais.toyswap.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentRepository(app: Application) {

    var data = MutableLiveData<List<Comment>>()

    fun getCommentData(id: Long) : MutableLiveData<List<Comment>> {
        val listService : APIService = RetrofitInstance.instance
        val call: Call<List<Comment>> = listService.getCommentsByListing(id)

        call.enqueue(object: Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                val retrievedListings = response.body()
                data.value = retrievedListings!!
            }

            override fun onFailure(p0: Call<List<Comment>>, p1: Throwable) {
                // Stretch goal to complete on failure.
            }
        })
        return data
    }
}