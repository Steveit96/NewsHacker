package com.steven.newshacker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.network.StoryApiHelper
import com.steven.newshacker.repository.StoryRepository
import com.steven.newshacker.ui.beststories.BestStoriesViewModel
import com.steven.newshacker.ui.comments.CommentsViewModel
import com.steven.newshacker.ui.newstories.NewsStoriesViewModel
import com.steven.newshacker.ui.topstories.TopStoriesViewModel

class StoryViewModelFactory(private val storyApiHelper: StoryApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopStoriesViewModel::class.java)) {
            return TopStoriesViewModel(StoryRepository(storyApiHelper)) as T
        } else if (modelClass.isAssignableFrom(BestStoriesViewModel::class.java)) {
            return BestStoriesViewModel(StoryRepository(storyApiHelper)) as T
        } else if (modelClass.isAssignableFrom(NewsStoriesViewModel::class.java)) {
            return NewsStoriesViewModel(StoryRepository(storyApiHelper)) as T
        } else if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(StoryRepository(storyApiHelper)) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }

}