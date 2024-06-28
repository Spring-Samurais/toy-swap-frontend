package com.springsamurais.toyswap.ui.listing

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.ui.mainactivity.MainActivity

class ViewListingClickHandlers(private var context: Context) {

    fun backButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        context.startActivity(intent)
    }

}