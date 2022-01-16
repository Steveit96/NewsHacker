package com.steven.newshacker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.steven.newshacker.model.StoryModel

@Database(entities = [StoryModel::class], version = DatabaseConst.STORY_DATABASE_VERSION)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
}