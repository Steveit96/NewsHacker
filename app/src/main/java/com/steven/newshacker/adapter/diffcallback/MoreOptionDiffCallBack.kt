package com.steven.newshacker.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.steven.newshacker.model.MoreOptionModel

class MoreOptionDiffCallBack : DiffUtil.ItemCallback<MoreOptionModel>() {
    override fun areItemsTheSame(oldItem: MoreOptionModel, newItem: MoreOptionModel) =
        oldItem.optionId == newItem.optionId

    override fun areContentsTheSame(oldItem: MoreOptionModel, newItem: MoreOptionModel) =
        oldItem == newItem
}