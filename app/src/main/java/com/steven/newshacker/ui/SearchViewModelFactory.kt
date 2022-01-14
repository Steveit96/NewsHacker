package com.steven.newshacker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.network.SearchApiHelper
import com.steven.newshacker.network.StoryApiHelper
import com.steven.newshacker.repository.SearchRepository
import com.steven.newshacker.repository.StoryRepository
import com.steven.newshacker.ui.search.SearchViewModel

class SearchViewModelFactory(
        private val searchApiHelper: SearchApiHelper,
        private val storyApiHelper: StoryApiHelper,
        ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(SearchRepository(searchApiHelper), StoryRepository(storyApiHelper)) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }

}