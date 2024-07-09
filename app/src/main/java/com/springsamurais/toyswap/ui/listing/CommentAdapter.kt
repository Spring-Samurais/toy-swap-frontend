package com.springsamurais.toyswap.ui.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.model.Comment
import com.springsamurais.toyswap.ui.mainactivity.RecyclerViewInterface
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(private val comments: List<Comment>,
                     private val context: Context,
                     private val recyclerViewInterface: RecyclerViewInterface)
    : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(view: View, recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(view) {
        val commentUser: TextView
        val commentBody: TextView
        val commentDate: TextView

        init {
            commentUser = view.findViewById(R.id.comment_username)
            commentBody = view.findViewById(R.id.comment_body)
            commentDate = view.findViewById(R.id.comment_date)

            view.setOnClickListener(View.OnClickListener { v: View? ->
                if (recyclerViewInterface != null) {
                    val position: Int = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position)
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.comment, viewGroup, false)
        return ViewHolder(view, recyclerViewInterface)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentComment: Comment = comments[position]

        // Format date in user-friendly way
        val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(currentComment.dateCommented!!)
        val formattedDate: String = SimpleDateFormat("dd-MM-yy | HH:mm", Locale.ENGLISH).format(date!!)

        viewHolder.commentUser.text = currentComment.commenter!!.nickname
        viewHolder.commentBody.text = currentComment.text
        viewHolder.commentDate.text = formattedDate

    }

    override fun getItemCount() = comments.size

}