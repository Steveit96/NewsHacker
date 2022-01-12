package com.steven.newshacker.adapter.viewholder

import android.text.Html
import android.view.View
import android.view.ViewGroup
import com.steven.newshacker.R
import com.steven.newshacker.databinding.LayoutCommentBinding
import com.steven.newshacker.listener.OnCommentsInteractionListener
import com.steven.newshacker.model.CommentModel


class CommentsViewHolder(
    parent: ViewGroup,
    val listener: OnCommentsInteractionListener,
                        )
    : BaseViewHolder(parent, R.layout.layout_comment) {

    val binding = LayoutCommentBinding.bind(itemView)

    fun bind(item: CommentModel) {
        binding.txtCommentAuthor.text =  item.by

        binding.txtCommentContent.text = Html.fromHtml(item.text)

        if (item.kids.isNotEmpty()) {
            binding.btnComment.visibility = View.VISIBLE
            binding.btnComment.text = "Comments (${item.kids.size})"
        } else {
            binding.btnComment.visibility = View.GONE
        }

        binding.btnComment.setOnClickListener {
            listener.commentsClicked(item)
        }
    }
}