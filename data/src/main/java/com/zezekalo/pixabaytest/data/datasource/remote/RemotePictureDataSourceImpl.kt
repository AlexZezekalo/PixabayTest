package com.zezekalo.pixabaytest.data.datasource.remote



import com.zezekalo.pixabaytest.data.BuildConfig
import com.zezekalo.pixabaytest.domain.entity.Picture
import retrofit2.Response
import javax.inject.Inject

class RemotePictureDataSourceImpl @Inject constructor(
    private val apiService: IRetrofitApiService,
) : RemotePictureDataSource {

    override suspend fun getPictures(query: String): Response<List<Picture>> =
        apiService.getPictures(
            key = BuildConfig.API_KEY,
            query = query,
            imageType = IMAGE_TYPE
        )

    companion object {

        private const val IMAGE_TYPE = "photo"
    }
}