package com.springsamurais.toyswap.ui.mainactivity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.model.Listing
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ListingAdapter(private val listings: List<Listing>,
                     private val context: Context)
    : RecyclerView.Adapter<ListingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listingImage: ImageView
        val listingName: TextView
        val locationText: TextView
        val usernameText: TextView
        val description: TextView
        val dateText: TextView

        init {
            listingImage = view.findViewById(R.id.listing_image)
            listingName  = view.findViewById(R.id.listing_name)
            locationText = view.findViewById(R.id.location_text)
            usernameText = view.findViewById(R.id.username_text)
            description  = view.findViewById(R.id.description_text)
            dateText     = view.findViewById(R.id.date_posted)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listing_item, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var bitmap: Bitmap? = null;
        val currentListing: Listing = listings[position]

        // Format the date in a user-friendly way
        val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(currentListing.datePosted!!)
        val formattedDate: String = SimpleDateFormat("dd-MM-yy", Locale.ENGLISH).format(date!!)

        viewHolder.listingName.text  = currentListing.title
        viewHolder.locationText.text = currentListing.member!!.location
        viewHolder.usernameText.text = currentListing.member!!.nickname
        viewHolder.description.text  = currentListing.description
        viewHolder.dateText.text     = formattedDate

        // Convert image and display with Glide
        if (currentListing.photo != null) {
            val byteArray = Base64.getDecoder().decode(currentListing.photo!!)
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        Glide.with(context)
            .load(bitmap)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(viewHolder.listingImage)
    }

    override fun getItemCount() = listings.size

}