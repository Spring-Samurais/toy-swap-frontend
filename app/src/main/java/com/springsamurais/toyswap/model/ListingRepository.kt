package com.springsamurais.toyswap.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.springsamurais.toyswap.service.APIService
import com.springsamurais.toyswap.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListingRepository(app: Application) {

    var data = MutableLiveData<List<Listing>>()

    fun getData() : MutableLiveData<List<Listing>> {
        val listService : APIService = RetrofitInstance.instance
        var call: Call<List<Listing>> = listService.getListings()

        call.enqueue(object: Callback<List<Listing>> {
            override fun onResponse(call: Call<List<Listing>>, response: Response<List<Listing>>) {
                val retrievedListings = response.body()
                data.value = retrievedListings
            }

            override fun onFailure(p0: Call<List<Listing>>, p1: Throwable) {
                // Stretch task: implement failure logic
            }
        })
        return data
    }
}