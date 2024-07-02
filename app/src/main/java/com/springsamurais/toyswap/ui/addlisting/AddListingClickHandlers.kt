package com.springsamurais.toyswap.ui.addlisting

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.springsamurais.toyswap.ui.mainactivity.MainActivity


class AddListingClickHandlers(private var context: Context) {

    fun captureImageButtonClicked(view: View) {
        Toast.makeText(context, "Capture Clicked", Toast.LENGTH_SHORT).show()
    }



    fun accessGalleryButtonClicked(view: View) {
        Toast.makeText(context, "Gallery Clicked", Toast.LENGTH_SHORT).show()
    }

    fun addListingButtonClicked(view: View) {
        Toast.makeText(context, "Add Listing Clicked", Toast.LENGTH_SHORT).show()
    }

    fun cancelButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        context.startActivity(intent)
    }
}