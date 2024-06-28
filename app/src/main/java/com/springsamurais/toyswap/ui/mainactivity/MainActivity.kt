package com.springsamurais.toyswap.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var handler: MainActivityClickHandlers? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        handler = MainActivityClickHandlers(this);
        binding?.setClickHandler(handler)
    }
}