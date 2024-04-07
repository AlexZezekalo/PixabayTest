package com.zezekalo.pixabaytest.data.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class RemotePicture(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("previewURL") val thumbnailUrl: String,
    @SerializedName("largeImageURL") val bigImageUrl: String?,
    @SerializedName("likes") val likeCount: Int,
    @SerializedName("downloads") val downloadCount: Int,
    @SerializedName("comments") val commentCount: Int
)