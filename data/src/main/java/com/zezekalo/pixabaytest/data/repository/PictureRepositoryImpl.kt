package com.zezekalo.pixabaytest.data.repository

import com.zezekalo.pixabaytest.data.datasource.local.LocalPictureDataSource
import com.zezekalo.pixabaytest.data.datasource.mapper.toLocal
import com.zezekalo.pixabaytest.data.datasource.remote.RemotePictureDataSource
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.exception.DomainException
import com.zezekalo.pixabaytest.domain.logger.logE
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
            withContext(Dispatchers.Default) {
                localPictureDataSource.cleanAndStorePictures(remotePictures.toLocal())
            }
            Result.success(Unit)
        } catch (throwable: Throwable) {
            logE("Caught an exception ${throwable.message}", throwable)
            localPictureDataSource.cleanPictures()
            Result.failure(throwable)
        }

    override suspend fun getPictureById(pictureId: Int): Result<Picture> {
        val picture = localPictureDataSource.getPictureById(pictureId)
        return if (picture != null) {
            Result.success(picture)
        } else {
            Result.failure(DomainException.PictureNotFound())
        }
    }
}