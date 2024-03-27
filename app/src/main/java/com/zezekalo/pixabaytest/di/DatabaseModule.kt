package com.zezekalo.pixabaytest.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.zezekalo.pixabaytest.data.datasource.local.room.AppDatabase
import com.zezekalo.pixabaytest.data.datasource.local.room.converter.RoomTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        roomTypeConverter: RoomTypeConverter
    ): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addTypeConverter(roomTypeConverter)
            .build()

    @Singleton
    @Provides
    fun provideTypeConverter(gson: Gson): RoomTypeConverter =
        RoomTypeConverter(gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    companion object {
        private const val DATABASE_NAME = "pixabay-local-storage"
    }
}