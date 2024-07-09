package com.springsamurais.toyswap.ui.listing

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.ui.mainactivity.MainActivity
import com.springsamurais.toyswap.ui.updatelisting.UpdateListingActivity

class ViewListingClickHandlers(private var context: Context) {

    fun addCommentButtonClicked(view: View) {
        val intent = Intent(view.context, AddCommentActivity::class.java)
        context.startActivity(intent)
    }

    fun backButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        context.startActivity(intent)
    }

}