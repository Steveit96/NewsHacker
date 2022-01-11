package com.steven.newshacker.adapter.viewholder

import android.view.ViewGroup
import com.steven.newshacker.R
import com.steven.newshacker.databinding.LayoutMoreOptionBinding
import com.steven.newshacker.listener.OnMoreItemInteractionListener
import com.steven.newshacker.model.MoreOptionModel

class MoreOptionViewHolder(
    parent: ViewGroup,
    val listener: OnMoreItemInteractionListener,
) : BaseViewHolder(parent, R.layout.layout_more_option) {

    val binding = LayoutMoreOptionBinding.bind(itemView)

    fun bind(item: MoreOptionModel) {
        binding.txtOption.text =  item.optionName
    }
}