package com.steven.newshacker.ui.topstories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mindorks.retrofit.coroutines.utils.Resource
import com.steven.newshacker.repository.TopStoriesRepository
import kotlinx.coroutines.Dispatchers

class TopStoriesViewModel(private val mainRepository: TopStoriesRepository) : ViewModel() {

    fun getStoriesById(storyId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.fetchTopStories(storyId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTopStoriesIDList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.fetchTopStoriesIDList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}