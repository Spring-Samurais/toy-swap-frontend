package com.springsamurais.toyswap.ui.mainactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.model.ListingRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListingRepository = ListingRepository(application)

    fun getData(): MutableLiveData<List<Listing>> {
        return repository.getLiveData()
    }
}