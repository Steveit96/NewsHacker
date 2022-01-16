package com.steven.newshacker.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.steven.newshacker.db.DatabaseConst
import com.steven.newshacker.db.RoomConverters
import javax.annotation.Nullable

@Entity(tableName = DatabaseConst.STORY_TABLE)
@TypeConverters(RoomConverters::class)
data class StoryModel(
        @PrimaryKey(autoGenerate = false)
        @SerializedName("id")
        var id: Int = 0,

        @SerializedName("by")
        var by : String = "",

        @SerializedName("descendants")
        var descendants: Int = 0,

        @SerializedName("kids")
        var kids: List<Int> ?= emptyList(),

        @Nullable
        @SerializedName("score")
        var score: Int = 0,

        @SerializedName("time")
        var time: Long = 0L,

        @SerializedName("title")
        var title: String = "",

        @SerializedName("type")
        var type: String = "",

        @SerializedName("url")
        var url: String = "",

        var storyType: String = "",
)