package com.steven.newshacker.listener

import com.steven.newshacker.model.StoryModel

interface OnStoryItemInteractionListener {

    fun onCommentClicked(story: StoryModel)

    fun onItemClicked(story: StoryModel)

    fun onArticleClicked(story: StoryModel)

}