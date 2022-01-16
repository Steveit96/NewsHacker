package com.steven.newshacker.repository

import com.steven.newshacker.model.CommentModel
import com.steven.newshacker.model.StoryModel
import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.offlinecaching.helper.util.Resource

interface StoryRepository {

    suspend fun deleteAllStories(storyType: String)

    suspend fun fetchStoriesIDList(storyType: String): List<Long>

    suspend fun fetchStoryById(storyId: String, storyType: String): StoryModel

    suspend fun fetchCommentsById(commentID: String): CommentModel

    suspend fun insertAllData(storyList: List<StoryModel>)

    suspend fun fetchAllStories(storyType: String): List<StoryModel>
}