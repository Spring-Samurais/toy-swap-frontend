package com.springsamurais.toyswap.ui.login

import android.content.Context
import android.content.Intent
import android.view.View

class LoginActivityClickHandlers(private var context: Context) {

    fun signUpButtonClicked(view: View) {
        val intent = Intent(view.context, SignUpActivity::class.java)
        context.startActivity(intent)
    }
}