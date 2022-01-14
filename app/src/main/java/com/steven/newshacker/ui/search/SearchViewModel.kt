package com.steven.newshacker.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mindorks.retrofit.coroutines.utils.Resource
import com.steven.newshacker.repository.SearchRepository
import com.steven.newshacker.repository.StoryRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val searchRepository: SearchRepository,
                   private val  storyRepository: StoryRepository) : ViewModel() {

    fun getStoryBySearchQuery(
            hitsPerPage: String,
            tags: String,
            attributesToRetrieve: String,
            attributesToHighlight: String,
            query: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = searchRepository.fetchSearchStoryByQuery(
                    hitsPerPage,
                    tags,
                    attributesToRetrieve,
                    attributesToHighlight,
                    query
            )))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getStoriesById(storyId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = storyRepository.fetchStoryById(storyId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}