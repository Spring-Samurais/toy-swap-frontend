package com.springsamurais.toyswap.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.ui.addlisting.AddListingActivity

class MainActivityClickHandlers(private var context: Context, private var user: Member) {

    fun addListingButtonClicked(view: View) {
        val intent = Intent(view.context, AddListingActivity::class.java)
        intent.putExtra("USER", user)
        context.startActivity(intent)
    }

    fun loginButtonClicked() {

    }
}