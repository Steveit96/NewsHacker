package com.steven.newshacker.db

import androidx.room.*
import com.steven.newshacker.model.StoryModel

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(story: List<StoryModel>)

    @Query("DELETE FROM ${DatabaseConst.STORY_TABLE} WHERE storyType == :storyType")
    suspend fun deleteAll(storyType: String)

    @Query("SELECT * FROM ${DatabaseConst.STORY_TABLE} WHERE storyType == :storyType")
    suspend fun getStories(storyType: String): List<StoryModel>
}