package com.springsamurais.toyswap.ui.listing

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityViewListingBinding
import com.springsamurais.toyswap.model.Listing

class ViewListingActivity : AppCompatActivity() {

    private var binding: ActivityViewListingBinding? = null
    private var handler: ViewListingClickHandlers? = null;
    private var listing: Listing? = null;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_listing)

        listing = intent.getParcelableExtra("LISTING_ITEM", Listing::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_listing)
        handler = ViewListingClickHandlers(this)
        binding?.clickHandler = handler
        binding?.listing = listing

    }
}