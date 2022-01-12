package com.steven.newshacker.adapter.viewholder

import android.text.format.DateFormat.format
import android.view.View
import android.view.ViewGroup
import com.steven.newshacker.R
import com.steven.newshacker.databinding.LayoutStoryBinding
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel
import java.lang.String.format
import java.text.DateFormat
import java.util.*

class StoryViewHolder(
    parent: ViewGroup,
    val listener: OnStoryItemInteractionListener,
) : BaseViewHolder(parent, R.layout.layout_story) {

    val binding = LayoutStoryBinding.bind(itemView)

    fun bind(item: StoryModel, isLastItem: Boolean) {
        binding.labelPointValue.text = item.score.toString()

        binding.labelStoryTitle.text = item.title

        binding.labelStoryAuthor.text = item.by

        binding.labelPointTxt.text = context.getString(R.string.string_label_point)

        binding.labelStoryCreatedAt.text = getDate(item.time)

        if(isLastItem) {
            binding.progressView.visibility = View.VISIBLE
        } else {
            binding.progressView.visibility = View.GONE
        }

        binding.btnArticle.setOnClickListener {
            listener.onArticleClicked(item)
        }

        binding.btnComment.setOnClickListener {
            listener.onCommentClicked(item)
        }
    }

    private fun getDate(time: Long): String? {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        return android.text.format.DateFormat.format("dd-MM-yyyy", cal).toString()
    }
}