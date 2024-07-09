package com.springsamurais.toyswap.ui.listing

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityAddCommentBinding
import com.springsamurais.toyswap.model.Comment
import com.springsamurais.toyswap.model.CommentRequest
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.model.Member
import com.springsamurais.toyswap.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCommentBinding
    private lateinit var handler: AddCommentClickHandlers
    private lateinit var currentUser: Member
    private lateinit var currentListing: Listing

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)

        currentListing = intent.getParcelableExtra("LISTING", Listing::class.java)!!
        currentUser = intent.getParcelableExtra("USER", Member::class.java)!!

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_comment)
        handler = AddCommentClickHandlers(this, currentUser, currentListing)
        binding.clickHandler = handler

        val userText: TextView = findViewById(R.id.user_info)
        userText.text = "What you saying, ${currentUser.username}?"

        val commentInput: EditText = findViewById(R.id.comment_text_input)
        val postBtn: Button = findViewById(R.id.add_comment_button)

        postBtn.setOnClickListener {
            val commentBody = commentInput.text.toString()
            if (commentBody.isEmpty()) {
                Toast.makeText(this, "Comments must contain a comment!", Toast.LENGTH_SHORT).show()
            } else {
                val commentToPost = CommentRequest(
                    commentBody,
                    currentUser.id!!,
                    currentListing.id!!,
                )
                postComment(commentToPost)
            }
        }
    }

    private fun postComment(comment: CommentRequest) {

        RetrofitInstance.instance.postComment(comment).enqueue(object: Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddCommentActivity, "Comments posted successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddCommentActivity, ViewListingActivity::class.java)
                    intent.putExtra("LISTING_ITEM", currentListing)
                    intent.putExtra("USER", currentUser)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@AddCommentActivity, "Comment failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Comment>, err: Throwable) {
                Toast.makeText(this@AddCommentActivity, "Comment response failed.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}