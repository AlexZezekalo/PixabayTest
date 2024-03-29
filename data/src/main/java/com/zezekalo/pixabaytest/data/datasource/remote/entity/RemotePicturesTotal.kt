package com.zezekalo.pixabaytest.data.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class RemotePicturesTotal(
    @SerializedName("total" ) val total: Int,
    @SerializedName("totalHits") val totalPictures: Int,
    @SerializedName("hits") val pictures: List<RemotePicture>,
)