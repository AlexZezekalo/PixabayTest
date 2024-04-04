package com.zezekalo.pixabaytest.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zezekalo.pixabaytest.presentation.entity.UiPicture

class PicturesDiffUtilCallback : DiffUtil.ItemCallback<UiPicture>() {
    override fun areItemsTheSame(oldItem: UiPicture, newItem: UiPicture): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UiPicture, newItem: UiPicture): Boolean =
        oldItem == newItem
}