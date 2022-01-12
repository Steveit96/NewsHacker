package com.steven.newshacker.ui.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mindorks.retrofit.coroutines.utils.Resource
import com.steven.newshacker.repository.StoryRepository
import kotlinx.coroutines.Dispatchers

class CommentsViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getComments(commentID: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.fetchCommentsById(commentID)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}