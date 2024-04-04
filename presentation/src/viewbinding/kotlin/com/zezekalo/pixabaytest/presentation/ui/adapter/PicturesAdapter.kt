package com.zezekalo.pixabaytest.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.zezekalo.pixabaytest.presentation.R
import com.zezekalo.pixabaytest.presentation.databinding.PictureItemLayoutBinding
import com.zezekalo.pixabaytest.presentation.entity.UiPicture

class PicturesAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<UiPicture>,
    private val onItemClick: (UiPicture?) -> Unit,
) : ListAdapter<UiPicture, PicturesAdapter.PicturesViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val binding =
            PictureItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PicturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PicturesViewHolder(
        private val binding: PictureItemLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var item: UiPicture? = null

        init {
            binding.root.setOnClickListener { onItemClick(item) }
        }

        fun bind(item: UiPicture) {
            this.item = item

            with(binding) {
                val resource = root.context.resources
                user.text = resource.getString(R.string.user_template, item.user)
                tags.text = resource.getString(R.string.tags_template, item.tagsAsString())
                thumbnail.load(item.thumbnailUrl){
                    crossfade(true)
                    crossfade(500)
                    transformations(RoundedCornersTransformation(8f))
                }
            }
        }
    }
}