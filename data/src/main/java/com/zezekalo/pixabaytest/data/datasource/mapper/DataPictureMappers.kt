package com.zezekalo.pixabaytest.data.datasource.mapper

import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalPicture
import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalTagList
import com.zezekalo.pixabaytest.data.datasource.remote.entity.RemotePicture
import com.zezekalo.pixabaytest.domain.entity.Picture


fun List<LocalPicture>.toDomain() = map(LocalPicture::toDomain)

fun List<RemotePicture>.toLocal() = map(RemotePicture::toLocal)

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

fun RemotePicture.toLocal(): LocalPicture =
    LocalPicture(
        id = id,
        user = user,
        tagList = LocalTagList(tags.split(",")),
        thumbnailUrl = thumbnailUrl,
        bigImageUrl = bigImageUrl ?: "",
        likeCount = likeCount,
        downloadCount = downloadCount,
        commentCount = commentCount
    )