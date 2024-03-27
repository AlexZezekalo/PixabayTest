package com.zezekalo.pixabaytest.data.datasource.remote

import com.zezekalo.pixabaytest.domain.entity.Picture
import retrofit2.Response

interface RemotePictureDataSource {

    suspend fun getPictures(query: String): Response<List<Picture>>
}
