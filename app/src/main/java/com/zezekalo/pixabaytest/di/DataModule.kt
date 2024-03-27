package com.zezekalo.pixabaytest.di

import com.zezekalo.pixabaytest.data.datasource.local.LocalPictureDataSource
import com.zezekalo.pixabaytest.data.datasource.local.LocalPictureDataSourceImpl
import com.zezekalo.pixabaytest.data.datasource.remote.RemotePictureDataSource
import com.zezekalo.pixabaytest.data.datasource.remote.RemotePictureDataSourceImpl
import com.zezekalo.pixabaytest.data.repository.PictureRepositoryImpl
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

    @Binds
    fun bindRemotePictureDataSource(remotePictureDataSourceImpl: RemotePictureDataSourceImpl): RemotePictureDataSource

    @Binds
    fun bindLocalPictureDataSource(localPictureDatasourceImpl: LocalPictureDataSourceImpl): LocalPictureDataSource
}