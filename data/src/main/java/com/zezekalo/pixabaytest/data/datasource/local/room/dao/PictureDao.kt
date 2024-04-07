package com.zezekalo.pixabaytest.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalPicture
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {

    @Query("SELECT * FROM pictures")
    fun observeAllPictures(): Flow<List<LocalPicture>>

    @Insert
    fun insertInDb(pictures: List<LocalPicture>)

    @Query("SELECT * FROM pictures WHERE id = :id")
    fun getPictureById(id: Int): LocalPicture?

    @Query("DELETE FROM pictures")
    fun clean()

    @Transaction
    fun cleanAndInsertInDb(pictures: List<LocalPicture>) {
        clean()
        insertInDb(pictures)
    }
}