package com.springsamurais.toyswap.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.springsamurais.toyswap.DummyData
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var handler: MainActivityClickHandlers? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set bindings for handling button clicks
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        handler = MainActivityClickHandlers(this);
        binding?.setClickHandler(handler)

        // Initialise and set-up RecyclerView
        val data = DummyData().getFakeListings()
        val adapter = ListingAdapter(data)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}