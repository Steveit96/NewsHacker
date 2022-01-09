package com.steven.newshacker.repository

import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.network.ApiInterface

class TopStoriesRepository(private val apiHelper: ApiHelper) {

    suspend fun fetchTopStories(storyId: String) = apiHelper.fetchTopStories(storyId)

    suspend fun fetchTopStoriesIDList() = apiHelper.fetchTopStoriesIDList()

}