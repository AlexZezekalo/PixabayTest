package com.zezekalo.pixabaytest.data.repository

import com.zezekalo.pixabaytest.data.datasource.local.LocalPictureDataSource
import com.zezekalo.pixabaytest.data.datasource.remote.RemotePictureDataSource
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val localPictureDataSource: LocalPictureDataSource,
    private val remotePictureDataSource: RemotePictureDataSource,
) : PictureRepository {

    override val picturesFlow: Flow<List<Picture>>
        get() = localPictureDataSource.picturesFlow

    override suspend fun queryPictures(queryString: String) {
        TODO("Not yet implemented")
    }
}