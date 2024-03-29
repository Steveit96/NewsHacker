package com.steven.newshacker.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.steven.newshacker.adapter.diffcallback.StoryDiffCallBack
import com.steven.newshacker.adapter.viewholder.StoryViewHolder
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel

class StoryAdapter(
    private val listener: OnStoryItemInteractionListener,
) : ListAdapter<StoryModel, StoryViewHolder>(StoryDiffCallBack()) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position), position == currentList.size-1)
    }
}