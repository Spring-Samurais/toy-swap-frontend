package com.springsamurais.toyswap.ui.listing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityAddCommentBinding
import com.springsamurais.toyswap.ui.mainactivity.MainActivityClickHandlers

class AddCommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCommentBinding
    private lateinit var handler: AddCommentClickHandlers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_comment)
        handler = AddCommentClickHandlers(this)
        binding.clickHandler = handler
    }
}