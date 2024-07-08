package com.springsamurais.toyswap.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.view.View
import com.springsamurais.toyswap.ui.addlisting.AddListingActivity
import com.springsamurais.toyswap.ui.login.ActivityResultLauncher

class MainActivityClickHandlers(private var context: Context) {

    fun addListingButtonClicked(view: View) {
        val intent = Intent(view.context, AddListingActivity::class.java)
        context.startActivity(intent)
    }

    fun loginButtonClicked(view: View) {
        val intent =Intent(view.context, ActivityResultLauncher::class.java)
        context.startActivity(intent)

    }
}