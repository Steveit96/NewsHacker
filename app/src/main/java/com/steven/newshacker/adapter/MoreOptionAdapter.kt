package com.steven.newshacker.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.steven.newshacker.adapter.diffcallback.MoreOptionDiffCallBack
import com.steven.newshacker.adapter.diffcallback.StoryDiffCallBack
import com.steven.newshacker.adapter.viewholder.MoreOptionViewHolder
import com.steven.newshacker.adapter.viewholder.StoryViewHolder
import com.steven.newshacker.listener.OnMoreItemInteractionListener
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.MoreOptionModel
import com.steven.newshacker.model.StoryModel

class MoreOptionAdapter(
    private val listener: OnMoreItemInteractionListener,
) : ListAdapter<MoreOptionModel, MoreOptionViewHolder>(MoreOptionDiffCallBack()) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreOptionViewHolder {
        return MoreOptionViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: MoreOptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}