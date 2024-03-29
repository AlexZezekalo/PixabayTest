package com.zezekalo.pixabaytest.data.repository

import com.zezekalo.pixabaytest.data.datasource.local.LocalPictureDataSource
import com.zezekalo.pixabaytest.data.datasource.mapper.toLocal
import com.zezekalo.pixabaytest.data.datasource.remote.RemotePictureDataSource
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.logger.logE
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val localPictureDataSource: LocalPictureDataSource,
    private val remotePictureDataSource: RemotePictureDataSource,
) : PictureRepository {

    override val picturesFlow: Flow<List<Picture>>
        get() = localPictureDataSource.picturesFlow

    override suspend fun queryPictures(queryString: String): Result<Unit> =
        try {
            val remotePictures = remotePictureDataSource.getPictures(queryString)
            localPictureDataSource.cleanAndStorePictures(remotePictures.toLocal())
            Result.success(Unit)
        } catch (throwable: Throwable) {
            logE("Caught an exception ${throwable.message}", throwable)
            Result.failure(throwable)
        }

}