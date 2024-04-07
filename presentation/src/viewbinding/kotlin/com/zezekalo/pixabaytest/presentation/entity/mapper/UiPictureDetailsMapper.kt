package com.zezekalo.pixabaytest.presentation.entity.mapper

import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.mapper.SimpleMapper
import com.zezekalo.pixabaytest.presentation.entity.UiPictureDetails
import javax.inject.Inject

class UiPictureDetailsMapper @Inject constructor(): SimpleMapper<Picture, UiPictureDetails> {
    override fun map(item: Picture): UiPictureDetails =
        UiPictureDetails(
            id = item.id,
            user = item.user,
            tags = item.tags,
            bigImageUrl = item.bigImageUrl,
            likeCount = item.likeCount,
            commentCount = item.commentCount,
            downloadCount = item.downloadCount,
        )
}