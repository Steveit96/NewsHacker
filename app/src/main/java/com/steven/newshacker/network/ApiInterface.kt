package com.steven.newshacker.network

import com.steven.newshacker.model.StoryModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("topstories.json?print=pretty")
    suspend fun getTopStoriesIDList(): List<Long>

    @GET("item/{storyId}.json?print=pretty")
    suspend fun getTopStories(@Path("storyId") storyId: String) : StoryModel
}