package com.springsamurais.toyswap.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityMainBinding
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.ui.listing.ViewListingActivity

class MainActivity : AppCompatActivity(), RecyclerViewInterface {

    private var listings: List<Listing>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: MainActivityClickHandlers
    private lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set ViewModel
        model = ViewModelProvider(this)[MainActivityViewModel::class.java]

        // Set bindings for handling button clicks
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        handler = MainActivityClickHandlers(this);
        binding.setClickHandler(handler)

        getAllListings()
    }

    private fun getAllListings() {

        val listingsObserver = Observer<List<Listing>> {listingData ->
            listings = listingData
            displayInRecyclerView()
        }
        model.getData().observe(this, listingsObserver)
    }

    private fun displayInRecyclerView() {
        val adapter = ListingAdapter(listings!!, this, this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this@MainActivity, ViewListingActivity::class.java)
        startActivity(intent)
    }
}
