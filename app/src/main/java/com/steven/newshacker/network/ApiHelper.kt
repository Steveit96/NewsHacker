package com.steven.newshacker.network

class ApiHelper(private val apiInterface: ApiInterface) {

    // Fetch the list of ID's based on the given storyType
    suspend fun fetchStoryIDList(storyType: String) = apiInterface.getStoryIDList(storyType)

    // Fetch the story details based on the ID
    suspend fun fetchStoryById(storyId: String) = apiInterface.getTopStoryById(storyId)
}