package com.steven.newshacker.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutResId: Int,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutResId, parent, false),
) {
    val view: View = itemView.rootView
    val context: Context = itemView.context

    fun getPxFromDimen(@DimenRes dimenRes: Int): Int {
        return context.resources.getDimension(dimenRes).toInt()
    }

    // This method will be soon deprecated since we migrate to ViewBinding
    fun <T : View> findById(@IdRes id: Int): T {
        return itemView.findViewById(id)!! // force throw exception if no view found
    }
}