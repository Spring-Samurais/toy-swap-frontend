package com.springsamurais.toyswap.ui.addlisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityAddListingBinding
import com.springsamurais.toyswap.databinding.ActivityMainBinding
import com.springsamurais.toyswap.ui.mainactivity.MainActivityClickHandlers

class AddListingActivity : AppCompatActivity() {

    private var binding: ActivityAddListingBinding? = null
    private var handler: AddListingClickHandlers? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_listing)
        handler = AddListingClickHandlers(this)
        binding?.clickHandler = handler
    }
}