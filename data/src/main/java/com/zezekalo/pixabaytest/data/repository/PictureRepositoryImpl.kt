package com.zezekalo.pixabaytest.data.repository

import com.zezekalo.pixabaytest.data.datasource.PictureDataSource
import com.zezekalo.pixabaytest.domain.di.Local
import com.zezekalo.pixabaytest.domain.di.Remote
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    @Local val localPictureDataSource: PictureDataSource,
    @Remote val remotePictureDataSource: PictureDataSource,
) : PictureRepository {
    override val picturesFlow: Flow<List<Picture>>
        get() = TODO("Not yet implemented")
}