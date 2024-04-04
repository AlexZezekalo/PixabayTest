package com.zezekalo.pixabaytest.data.datasource.remote

import com.zezekalo.pixabaytest.data.datasource.remote.entity.RemotePicturesTotal
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitApiService {

    @GET(".")
    suspend fun getPictures(
        @Query(value = "key") key: String,
        @Query(value = "q") query: String,
        @Query(value = "image_type") imageType: String,
    ): RemotePicturesTotal
}