package com.zezekalo.pixabaytest.data.datasource.remote

import com.zezekalo.pixabaytest.data.datasource.remote.entity.RemotePicture

interface RemotePictureDataSource {

    suspend fun getPictures(query: String): List<RemotePicture>
}
