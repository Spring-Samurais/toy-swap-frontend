package com.springsamurais.toyswap.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.service.RetrofitInstance
import com.springsamurais.toyswap.ui.login.data.LoginRequest
import com.springsamurais.toyswap.ui.login.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.username_input)
        val passwordEditText = findViewById<EditText>(R.id.password_input)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            } else {
                login(username, password)
            }
        }
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        RetrofitInstance.instance.login(loginRequest).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Toast.makeText(this@LoginActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    // Handle post login here
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, error: Throwable) {
                Toast.makeText(this@LoginActivity, "Login failed. ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}