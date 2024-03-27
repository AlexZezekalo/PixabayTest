package com.zezekalo.pixabaytest.data.datasource.local

import com.zezekalo.pixabaytest.data.datasource.local.room.AppDatabase
import com.zezekalo.pixabaytest.data.datasource.mapper.toDomain
import com.zezekalo.pixabaytest.data.datasource.mapper.toLocal
import com.zezekalo.pixabaytest.domain.entity.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPictureDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase,
): LocalPictureDataSource {

    override val picturesFlow: Flow<List<Picture>>
        get() = appDatabase.pictureDao().observeAllPictures().map { it.toDomain() }

    override suspend fun cleanAndStorePictures(pictures: List<Picture>) {
        appDatabase.pictureDao().cleanAndInsertInDb(pictures.map { it.toLocal() })
    }

    override suspend fun cleanPictures() {
        appDatabase.pictureDao().clean()
    }

}