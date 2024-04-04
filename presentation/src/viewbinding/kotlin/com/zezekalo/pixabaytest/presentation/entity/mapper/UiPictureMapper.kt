package com.zezekalo.pixabaytest.presentation.entity.mapper

import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.mapper.SimpleMapper
import com.zezekalo.pixabaytest.presentation.entity.UiPicture
import javax.inject.Inject

class UiPictureMapper @Inject constructor(): SimpleMapper<Picture, UiPicture> {
    override fun map(item: Picture): UiPicture =
        UiPicture(
            id = item.id,
            user = item.user,
            tags = item.tags,
            thumbnailUrl = item.thumbnailUrl
        )
}