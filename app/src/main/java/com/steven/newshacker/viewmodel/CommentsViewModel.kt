package com.steven.newshacker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.steven.newshacker.network.NetworkResource
import com.steven.newshacker.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
        repository: StoryRepository,
) : ViewModel() {

    var repository: StoryRepository = repository

    fun getComments(commentID: String) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = repository.fetchCommentsById(commentID)))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}