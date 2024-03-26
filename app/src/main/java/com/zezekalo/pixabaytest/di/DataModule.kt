package com.zezekalo.pixabaytest.di

import com.zezekalo.pixabaytest.data.datasource.PictureDataSource
import com.zezekalo.pixabaytest.data.datasource.local.LocalPictureDataSource
import com.zezekalo.pixabaytest.data.datasource.remote.RemotePictureDataSource
import com.zezekalo.pixabaytest.data.repository.PictureRepositoryImpl
import com.zezekalo.pixabaytest.domain.di.Local
import com.zezekalo.pixabaytest.domain.di.Remote
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindPictureRepository(pictureRepositoryImpl: PictureRepositoryImpl): PictureRepository

    @Remote
    @Binds
    fun bindRemotePictureDataSource(remotePictureDataSource: RemotePictureDataSource): PictureDataSource

    @Local
    @Binds
    fun bindLocalPictureDataSource(localPictureDatasource: LocalPictureDataSource): PictureDataSource

}