package com.steven.newshacker.listener

import com.steven.newshacker.model.CommentModel

interface OnCommentsInteractionListener {
    fun commentsClicked(comment: CommentModel)
}