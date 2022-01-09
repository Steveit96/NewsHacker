package com.steven.newshacker.model

data class StoryModel(
    val id: Int = 0,
    val by : String = "",
    val descendants: Int = 0,
    val kids: List<Int> = emptyList(),
    val score: Int = 0,
    val time: Long = 0L,
    val title: String = "",
    val type: String = "",
    val url: String = "",
)