package com.steven.newshacker.network

class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun fetchTopStories(storyId: String) = apiInterface.getTopStories(storyId)

    suspend fun fetchTopStoriesIDList() = apiInterface.getTopStoriesIDList()
}