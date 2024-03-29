package com.zezekalo.pixabaytest.data.datasource.local

import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalPicture
import com.zezekalo.pixabaytest.domain.entity.Picture
import kotlinx.coroutines.flow.Flow

interface LocalPictureDataSource {

    val picturesFlow: Flow<List<Picture>>

    suspend fun cleanAndStorePictures(pictures: List<LocalPicture>)

    suspend fun cleanPictures()
}
