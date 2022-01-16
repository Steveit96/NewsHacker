package com.steven.newshacker.network

import com.steven.newshacker.model.CommentModel
import com.steven.newshacker.model.StoryModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApiInterface {

    companion object {
        const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
    }

    @GET("{strorytype}.json?print=pretty")
    suspend fun getStoryIDList(@Path("strorytype") strorytype: String): List<Long>

    @GET("item/{storyId}.json?print=pretty")
    suspend fun getTopStoryById(@Path("storyId") storyId: String) : StoryModel

    @GET("item/{commentID}.json?print=pretty")
    suspend fun getCommentById(@Path("commentID") commentID: String) : CommentModel
}