package com.steven.newshacker.repository

import androidx.room.withTransaction
import com.steven.newshacker.db.StoryDatabase
import com.steven.newshacker.model.CommentModel
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.network.StoryApiInterface

class StoryRepositoryImpl(
        private val api: StoryApiInterface,
        private val storyDb: StoryDatabase,
): StoryRepository {

    private val storyDao = storyDb.storyDao()

    override suspend fun insertAllData(storyList: List<StoryModel>) {
        storyDb.withTransaction {
            storyDao.insert(storyList)
        }
    }

    override suspend fun fetchAllStories(storyType: String): List<StoryModel> {
        return storyDao.getStories(storyType)
    }

    override suspend fun deleteAllStories(storyType: String) {
        storyDb.withTransaction {
            storyDao.deleteAll(storyType)
        }
    }

    override suspend fun fetchStoriesIDList(storyType: String): List<Long> = api.getStoryIDList(storyType)

    override suspend fun fetchStoryById(storyId: String, storyType: String): StoryModel = api.getTopStoryById(storyId)

    override suspend fun fetchCommentsById(commentID: String): CommentModel = api.getCommentById(commentID)


}