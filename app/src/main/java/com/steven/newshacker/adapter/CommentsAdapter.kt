package com.steven.newshacker.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.steven.newshacker.adapter.diffcallback.CommentsDiffCallBack
import com.steven.newshacker.adapter.viewholder.CommentsViewHolder
import com.steven.newshacker.listener.OnCommentsInteractionListener
import com.steven.newshacker.model.CommentModel

class CommentsAdapter(private val listener: OnCommentsInteractionListener
) : ListAdapter<CommentModel, CommentsViewHolder>(CommentsDiffCallBack()) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}