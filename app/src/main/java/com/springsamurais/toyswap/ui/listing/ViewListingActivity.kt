package com.springsamurais.toyswap.ui.listing

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityViewListingBinding
import com.springsamurais.toyswap.model.Comment
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.service.APIService
import com.springsamurais.toyswap.service.RetrofitInstance
import com.springsamurais.toyswap.ui.updatelisting.UpdateListingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ViewListingActivity : AppCompatActivity(), OnMapReadyCallback {

    private var binding: ActivityViewListingBinding? = null
    private var handler: ViewListingClickHandlers? = null;
    private var listing: Listing? = null;
    private var comments: List<Comment>? = null;
    private lateinit var geocoder: Geocoder
    private lateinit var model: ViewListingViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_listing)

        listing = intent.getParcelableExtra("LISTING_ITEM", Listing::class.java)
        model = ViewModelProvider(this)[ViewListingViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_listing)
        handler = ViewListingClickHandlers(this)
        binding?.clickHandler = handler
        binding?.listing = listing

        setFormattedContent(listing!!)

        Glide.with(this)
            .load(listing!!.images!![0].url)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(findViewById(R.id.listing_full_image))

        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map_container, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)

        getListingComments(listing!!.id)


        val updateListingButton = findViewById<Button>(R.id.update_listing_button)

        updateListingButton.setOnClickListener {
            val intent = android.content.Intent(this, UpdateListingActivity::class.java)
            intent.putExtra("LISTING_ITEM", listing)
            startActivity(intent)
        }

        val deleteListingButton = findViewById<Button>(R.id.delete_listing_button)
        deleteListingButton.setOnClickListener {
            var apiService: APIService = RetrofitInstance.instance
             val listingID =listing!!.id!!.toString()
            apiService.deleteListing(
                listingID
            ).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ViewListingActivity, "Listing deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@ViewListingActivity, "Error deleting Listing: $errorBody", Toast.LENGTH_LONG).show()
                        Log.e("DeleteFail:", "Error response $errorBody")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    val errorMessage = t.message
                    Toast.makeText(this@ViewListingActivity, "Something went wrong $errorMessage", Toast.LENGTH_LONG).show()
                    Log.e("DeleteFailure:", "Error: ${t.message}", t)
                }
            })

        } // end of delete

    }

    private fun getListingComments(id: Long?) {

        val commentObserver = Observer<List<Comment>?> {commentData ->
            if (commentData != null) {
                comments = commentData
                displayInRecyclerView()
            }
        }
        model.getComments(id!!).observe(this, commentObserver)
    }

    private fun displayInRecyclerView() {
        val adapter = CommentAdapter(comments!!, this)
        val recyclerView: RecyclerView = findViewById(R.id.listing_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        adapter.notifyDataSetChanged()
    }

    override fun onMapReady(map: GoogleMap) {
        val coordinates = getCoordinates(listing!!.member!!.location!!)
        val itemLocation = LatLng(coordinates[0], coordinates[1])
        map.addMarker(
            MarkerOptions()
                .position(itemLocation)
                .title("Marker"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(itemLocation, 10.0f))
    }

    private fun setFormattedContent(listing: Listing) {
        // Select views from layout
        val dateText: TextView = findViewById(R.id.listing_full_date)
        val conditionText: TextView = findViewById(R.id.listing_full_condition)

        // Format fields as required
        val formattedCondition = listing.condition!!.replace("_", " ")

        val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(listing.datePosted!!)
        val formattedDate: String = SimpleDateFormat("dd-MM-yy", Locale.ENGLISH).format(date!!)

        // Set the text in the views
        conditionText.text = formattedCondition
        dateText.text = formattedDate
    }

    private fun getCoordinates(location: String) : Array<Double> {
        geocoder = Geocoder(this, Locale.ENGLISH)
        val address = geocoder.getFromLocationName(location, 1)?.get(0)
        return arrayOf(address!!.latitude, address.longitude)
    }
}