package com.springsamurais.toyswap.ui.listing

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.ui.mainactivity.MainActivity

class ViewListingClickHandlers(
    private var context: Context,
    private var user: Member,
    private var listing: Listing
) {

    fun addCommentButtonClicked(view: View) {
        val intent = Intent(view.context, AddCommentActivity::class.java)
        intent.putExtra("USER", user)
        intent.putExtra("LISTING", listing)
        context.startActivity(intent)
    }

    fun backButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.putExtra("USER", user)
        context.startActivity(intent)
    }

}