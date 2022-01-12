package com.steven.newshacker.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.steven.newshacker.model.CommentModel
import com.steven.newshacker.model.StoryModel

class CommentsDiffCallBack : DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel) =
        oldItem == newItem
}