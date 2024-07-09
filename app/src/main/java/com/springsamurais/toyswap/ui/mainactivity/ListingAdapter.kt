package com.springsamurais.toyswap.ui.mainactivity

import android.content.Context
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
import java.text.SimpleDateFormat
import java.util.*

class ListingAdapter(private val listings: List<Listing>,
                     private val context: Context,
                     private val recyclerViewInterface: RecyclerViewInterface)
    : RecyclerView.Adapter<ListingAdapter.ViewHolder>() {

    class ViewHolder(view: View, recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(view) {

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

            view.setOnClickListener(View.OnClickListener { v: View? ->
                if (recyclerViewInterface != null) {
                    val position: Int = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position)
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listing_item, viewGroup, false)
        return ViewHolder(view, recyclerViewInterface)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentListing: Listing = listings[position]

        // Format the date in a user-friendly way
        val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(currentListing.datePosted!!)
        val formattedDate: String = SimpleDateFormat("dd-MM-yy", Locale.ENGLISH).format(date!!)

        viewHolder.listingName.text  = currentListing.title
        viewHolder.locationText.text = currentListing.member!!.location
        viewHolder.usernameText.text = currentListing.member!!.username
        viewHolder.description.text  = currentListing.description
        viewHolder.dateText.text     = formattedDate

        // Display image with Glide
        Glide.with(context)
            .load(currentListing.images!![0].url)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(viewHolder.listingImage)
    }

    override fun getItemCount() = listings.size

}