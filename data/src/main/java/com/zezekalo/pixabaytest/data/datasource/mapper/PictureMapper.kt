package com.zezekalo.pixabaytest.data.datasource.mapper

import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalPicture
import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalTagList
import com.zezekalo.pixabaytest.domain.entity.Picture


fun List<LocalPicture>.toDomain() = map(LocalPicture::toDomain)

fun List<Picture>.toLocal() = map(Picture::toLocal)

fun LocalPicture.toDomain(): Picture =
    Picture(
        id = id,
        user = user,
        tags = tagList.tags,
        thumbnailUrl = thumbnailUrl,
        bigImageUrl = bigImageUrl,
        likeCount = likeCount,
        downloadCount = downloadCount,
        commentCount = commentCount
    )

fun Picture.toLocal(): LocalPicture =
    LocalPicture(
        id = id,
        user = user,
        tagList = LocalTagList(tags),
        thumbnailUrl = thumbnailUrl,
        bigImageUrl = bigImageUrl,
        likeCount = likeCount,
        downloadCount = downloadCount,
        commentCount = commentCount
    )
