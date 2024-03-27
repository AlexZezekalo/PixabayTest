package com.zezekalo.pixabaytest.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zezekalo.pixabaytest.data.datasource.local.room.converter.RoomTypeConverter
import com.zezekalo.pixabaytest.data.datasource.local.room.dao.PictureDao
import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalPicture


@TypeConverters(value = [RoomTypeConverter::class])
@Database(entities = [LocalPicture::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pictureDao(): PictureDao
}