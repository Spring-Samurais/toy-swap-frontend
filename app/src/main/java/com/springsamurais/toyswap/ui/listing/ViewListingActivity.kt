package com.springsamurais.toyswap.ui.listing

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityViewListingBinding
import com.springsamurais.toyswap.model.Listing

class ViewListingActivity : AppCompatActivity(), OnMapReadyCallback {

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

        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map_container, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        val itemLocation = LatLng(52.63, -1.69)
        map.addMarker(
            MarkerOptions()
                .position(itemLocation)
                .title("Marker"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(itemLocation, 10.0f))
    }
}