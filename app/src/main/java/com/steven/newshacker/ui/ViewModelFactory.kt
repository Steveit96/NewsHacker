package com.steven.newshacker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.repository.TopStoriesRepository
import com.steven.newshacker.ui.topstories.TopStoriesViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopStoriesViewModel::class.java)) {
            return TopStoriesViewModel(TopStoriesRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}