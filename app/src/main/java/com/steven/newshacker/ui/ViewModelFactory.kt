package com.steven.newshacker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.repository.StoryRepository
import com.steven.newshacker.ui.beststories.BestStoriesViewModel
import com.steven.newshacker.ui.newstories.NewsStoriesViewModel
import com.steven.newshacker.ui.topstories.TopStoriesViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopStoriesViewModel::class.java)) {
            return TopStoriesViewModel(StoryRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(BestStoriesViewModel::class.java)) {
            return BestStoriesViewModel(StoryRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(NewsStoriesViewModel::class.java)) {
            return NewsStoriesViewModel(StoryRepository(apiHelper)) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }

}