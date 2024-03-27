package com.zezekalo.pixabaytest.data.datasource.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures")
data class LocalPicture(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "tags") val tagList: LocalTagList,
    @ColumnInfo(name = "thumbnail") val thumbnailUrl: String,
    @ColumnInfo(name = "big_image") val bigImageUrl: String,
    @ColumnInfo(name = "likes") val likeCount: Int,
    @ColumnInfo(name = "downloads") val downloadCount: Int,
    @ColumnInfo(name = "comments") val commentCount: Int,
)