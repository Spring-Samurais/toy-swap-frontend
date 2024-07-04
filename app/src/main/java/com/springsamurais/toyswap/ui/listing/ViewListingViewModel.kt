package com.springsamurais.toyswap.ui.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.springsamurais.toyswap.model.Comment
import com.springsamurais.toyswap.model.CommentRepository

class ViewListingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CommentRepository = CommentRepository(application)

    fun getComments(id: Long): MutableLiveData<List<Comment>?> {
        return repository.getCommentData(id)
    }

}