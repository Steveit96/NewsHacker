package com.steven.newshacker.repository

import com.steven.newshacker.network.StoryApiHelper

class StoryRepository(private val storyApiHelper: StoryApiHelper) {

    suspend fun fetchStoriesIDList(storyType: String) = storyApiHelper.fetchStoryIDList(storyType)

    suspend fun fetchStoryById(storyId: String) = storyApiHelper.fetchStoryById(storyId)

    suspend fun fetchCommentsById(commentID: String) = storyApiHelper.fetchCommentsByID(commentID)

}