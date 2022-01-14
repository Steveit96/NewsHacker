package com.steven.newshacker.network

class StoryApiHelper(private val storyApiInterface: StoryApiInterface) {

    // Fetch the list of ID's based on the given storyType
    suspend fun fetchStoryIDList(storyType: String) = storyApiInterface.getStoryIDList(storyType)

    // Fetch the story details based on the ID
    suspend fun fetchStoryById(storyId: String) = storyApiInterface.getTopStoryById(storyId)

    // Fetch the story details based on the ID
    suspend fun fetchCommentsByID(commentID: String) = storyApiInterface.getCommentById(commentID)
}