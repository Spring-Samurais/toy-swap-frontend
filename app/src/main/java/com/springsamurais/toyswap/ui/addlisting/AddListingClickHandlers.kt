package com.springsamurais.toyswap.ui.addlisting

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.ui.mainactivity.MainActivity

class AddListingClickHandlers(private var context: Context) {

    fun captureImageButtonClicked(view: View) {
        // Moazam has code for opening camera
    }

    fun accessGalleryButtonClicked(view: View) {
        // Moazam has code for accessing gallery
    }

    fun addListingButtonClicked(view: View) {
        // To be implented after POST request designed
    }

    fun cancelButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        context.startActivity(intent)
    }
}