package com.steven.newshacker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.network.NetworkResource
import com.steven.newshacker.repository.StoryRepository
import com.steven.newshacker.repository.StoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.teamgravity.offlinecaching.helper.util.Resource
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(
        repository: StoryRepository,
) : ViewModel() {


   var repository: StoryRepository = repository

    fun insertAllStories(storyList: List<StoryModel>) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            repository.insertAllData(storyList)
            emit(NetworkResource.success(data = "Insert All"))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = "Error Occurred!"))
        }
    }

    fun deleteAllStories(storyType: String) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            repository.deleteAllStories(storyType)
            emit(NetworkResource.success(data = "Delete All $storyType"))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = "Error Occurred!"))
        }
    }

    fun fetchAllStoriesFromDB(storyType: String) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = repository.fetchAllStories(storyType)))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTopStoriesById(storyId: String, storyType: String) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = repository.fetchStoryById(storyId, storyType)))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTopStoriesIDList(storyType: String) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = repository.fetchStoriesIDList(storyType)))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }



}