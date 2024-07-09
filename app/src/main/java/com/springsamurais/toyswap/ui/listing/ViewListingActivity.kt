package com.springsamurais.toyswap.ui.listing

import android.app.AlertDialog
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.TextView
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
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.ui.mainactivity.RecyclerViewInterface
import java.text.SimpleDateFormat
import java.util.*

class ViewListingActivity : AppCompatActivity(), OnMapReadyCallback, RecyclerViewInterface {

    private var binding: ActivityViewListingBinding? = null
    private var handler: ViewListingClickHandlers? = null;
    private var listing: Listing? = null;
    private var comments: List<Comment>? = null;
    private lateinit var geocoder: Geocoder
    private lateinit var model: ViewListingViewModel
    private lateinit var currentUser: Member

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_listing)

        // Retrieve user and listing from parcel
        listing = intent.getParcelableExtra("LISTING_ITEM", Listing::class.java)
        currentUser = intent.getParcelableExtra("USER", Member::class.java)!!

        model = ViewModelProvider(this)[ViewListingViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_listing)
        handler = ViewListingClickHandlers(this, currentUser, listing!!)
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

        val userField: TextView = findViewById(R.id.view_listing_user_info)
        userField.text = "Liking this, ${currentUser.username}?"

        getListingComments(listing!!.id)
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
        val adapter = CommentAdapter(comments!!, this, this)
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

    override fun onItemClick(position: Int) {
        if (listing!!.member!!.id == currentUser.id) {
            displayDialog(position)
            updateAvailability(position)
        }
    }

    private fun updateAvailability(position: Int) {

    }

    private fun displayDialog(position: Int) {

        val builder  = AlertDialog.Builder(this)
        val builder2 = AlertDialog.Builder(this)

        builder.setTitle("Accept this comment?")
            .setMessage("This will mark your listing as 'unavailable' and share details with ${comments!![position].commenter!!.username}")
            .setPositiveButton("Accept") { dialog, which ->
                builder2.setTitle("Exchange details")
                    .setMessage(
                        "Email for user '${comments!![position].commenter!!.username}' is:" +
                                "\n\n${comments!![position].commenter!!.email}.\n\n" +
                                "Happy swapping!"
                    ).create().show()
            }
            .setNegativeButton("Cancel") { dialog, which -> }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}