package com.steven.newshacker.model

data class CommentModel(
    val by: String = "",
    val id: Long = 0L,
    val kids: List<Long> = emptyList(),
    val parent: Long = 0L,
    val text: String = "",
    val time: Long = 0L,
    val type: String = "")