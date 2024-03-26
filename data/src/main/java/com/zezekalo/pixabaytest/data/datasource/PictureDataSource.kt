package com.zezekalo.pixabaytest.data.datasource

import com.zezekalo.pixabaytest.domain.entity.Picture
import kotlinx.coroutines.flow.Flow

interface PictureDataSource {

    val picturesFlow: Flow<List<Picture>>

    suspend fun getPictures(query: String): List<Picture>

    suspend fun savePictures(pictures: List<Picture>)
}
