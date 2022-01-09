package com.steven.newshacker.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.steven.newshacker.model.StoryModel

class StoryDiffCallBack : DiffUtil.ItemCallback<StoryModel>() {
    override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel) =
        oldItem == newItem
}