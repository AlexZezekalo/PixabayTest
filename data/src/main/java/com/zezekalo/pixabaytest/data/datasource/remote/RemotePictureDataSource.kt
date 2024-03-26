package com.zezekalo.pixabaytest.data.datasource.remote

import com.zezekalo.pixabaytest.data.datasource.PictureDataSource
import com.zezekalo.pixabaytest.domain.entity.Picture
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePictureDataSource @Inject constructor() : PictureDataSource {
    override val picturesFlow: Flow<List<Picture>>
        get() = TODO("Not yet implemented")

    override suspend fun getPictures(query: String): List<Picture> {
        TODO("Not yet implemented")
    }

    override suspend fun savePictures(pictures: List<Picture>) {
        TODO("Not yet implemented")
    }
}