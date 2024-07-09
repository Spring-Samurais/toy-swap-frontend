package com.springsamurais.toyswap.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.service.RetrofitInstance
import com.springsamurais.toyswap.ui.mainactivity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val emailTxt: EditText = findViewById(R.id.email_field)
        val usernameTxt: EditText = findViewById(R.id.username_field)
        val passwordTxt: EditText = findViewById(R.id.password_field)
        val locationTxt: EditText = findViewById(R.id.location_field)
        val registerBtn: Button = findViewById(R.id.create_new_user_button)

        registerBtn.setOnClickListener {
            val email    = emailTxt.text.toString()
            val username = usernameTxt.text.toString()
            val password = passwordTxt.text.toString()
            val location = locationTxt.text.toString()

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            } else {
                val newMember = Member(null, null, email, username, location, null, password)
                signup(newMember)
            }
        }
    }

    private fun signup(member: Member) {

        RetrofitInstance.instance.register(member).enqueue(object: Callback<Member> {
            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                if (response.isSuccessful) {
                    val returnedMember = response.body()!!
                    Toast.makeText(this@SignUpActivity, "Registered successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Member>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Login response failure. ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}