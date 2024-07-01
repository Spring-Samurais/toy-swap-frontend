package com.springsamurais.toyswap.ui.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.model.Listing

class ListingAdapter(private val listings: List<Listing>) : RecyclerView.Adapter<ListingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listingImage: ImageView
        val listingName: TextView
//        val locationIcon: ImageView
        val locationText: TextView
//        val userIcon: ImageView
        val usernameText: TextView
        val description: TextView


        init {
            listingImage = view.findViewById(R.id.listing_image)
            listingName  = view.findViewById(R.id.listing_name)
            locationText = view.findViewById(R.id.location_text)
            usernameText = view.findViewById(R.id.username_text)
            description  = view.findViewById(R.id.description_text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listing_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentListing: Listing = listings[position]

        viewHolder.listingName.text = currentListing.title
        viewHolder.locationText.text = currentListing.member!!.location
        viewHolder.usernameText.text = currentListing.member!!.nickname
        viewHolder.description.text = currentListing.description

        viewHolder.listingImage.setImageResource(R.drawable.pikachu_toy)
    }

    override fun getItemCount() = listings.size

}