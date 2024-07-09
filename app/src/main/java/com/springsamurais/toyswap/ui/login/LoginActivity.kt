package com.springsamurais.toyswap.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityLoginBinding
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.service.RetrofitInstance
import com.springsamurais.toyswap.ui.login.data.LoginRequest
import com.springsamurais.toyswap.ui.login.data.LoginResponse
import com.springsamurais.toyswap.ui.mainactivity.MainActivity
import com.springsamurais.toyswap.ui.mainactivity.MainActivityClickHandlers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var handler: LoginActivityClickHandlers
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        // Set click handlers and binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        handler = LoginActivityClickHandlers(this)
        binding.clickHandler = handler

        val usernameEditText = findViewById<EditText>(R.id.username_input)
        val passwordEditText = findViewById<EditText>(R.id.password_input)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("LOGIN METHOD", "Details: $username and $password")
                login(username, password)
            }
        }
    }

    private fun login(username: String, password: String) {

        val loginRequest = LoginRequest(username, password)
        RetrofitInstance.instance.login(loginRequest).enqueue(object: Callback<Member> {

            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                if (response.isSuccessful) {
                    val returnedMember = response.body()!!
                    Toast.makeText(this@LoginActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("USER", returnedMember)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Member>, error: Throwable) {
                Toast.makeText(this@LoginActivity, "Login response failure. ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}