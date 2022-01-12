package com.steven.newshacker.repository

import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.network.ApiInterface

class StoryRepository(private val apiHelper: ApiHelper) {

    suspend fun fetchStoriesIDList(storyType: String) = apiHelper.fetchStoryIDList(storyType)

    suspend fun fetchStoryById(storyId: String) = apiHelper.fetchStoryById(storyId)

    suspend fun fetchCommentsById(commentID: String) = apiHelper.fetchCommentsByID(commentID)

}