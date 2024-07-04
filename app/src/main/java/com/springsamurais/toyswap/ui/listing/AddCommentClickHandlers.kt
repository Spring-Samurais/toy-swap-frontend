package com.springsamurais.toyswap.ui.listing

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.ui.mainactivity.MainActivity

class AddCommentClickHandlers(private var context: Context) {

    fun addCommentButtonClicked(view: View) {

    }

    fun cancelButtonClicked(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        context.startActivity(intent)
    }
}