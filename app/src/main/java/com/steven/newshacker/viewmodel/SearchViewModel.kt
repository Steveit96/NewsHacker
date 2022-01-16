package com.steven.newshacker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.steven.newshacker.network.NetworkResource
import com.steven.newshacker.repository.SearchRepository
import com.steven.newshacker.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
        storyRepository: StoryRepository,
        searchRepository: SearchRepository,
) : ViewModel() {

    var storyRepository: StoryRepository = storyRepository

    var searchRepository: SearchRepository = searchRepository

    fun getStoryBySearchQuery(
            hitsPerPage: String,
            tags: String,
            attributesToRetrieve: String,
            attributesToHighlight: String,
            query: String,
    ) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = searchRepository.fetchSearchStoryByQuery(
                    hitsPerPage,
                    tags,
                    attributesToRetrieve,
                    attributesToHighlight,
                    query
            )))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getStoriesById(storyId: String) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = storyRepository.fetchStoryById(storyId, "")))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}