package com.springsamurais.toyswap.ui.listing

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.model.Comment
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.service.RetrofitInstance
import com.springsamurais.toyswap.ui.mainactivity.MainActivity

class AddCommentClickHandlers(
    private var context: Context,
    private var user: Member,
    private var listing: Listing
) {

    fun addCommentButtonClicked(view: View) {

    }

    fun cancelButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.putExtra("USER", user)
        context.startActivity(intent)
    }
}

/* private String text;
private Long commenterId;
private Long listingId;
*/