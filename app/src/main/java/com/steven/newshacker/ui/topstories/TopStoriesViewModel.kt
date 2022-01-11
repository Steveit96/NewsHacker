package com.steven.newshacker.ui.topstories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mindorks.retrofit.coroutines.utils.Resource
import com.steven.newshacker.repository.StoryRepository
import kotlinx.coroutines.Dispatchers

class TopStoriesViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getTopStoriesById(storyId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.fetchStoryById(storyId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTopStoriesIDList(storyType: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.fetchStoriesIDList(storyType)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}