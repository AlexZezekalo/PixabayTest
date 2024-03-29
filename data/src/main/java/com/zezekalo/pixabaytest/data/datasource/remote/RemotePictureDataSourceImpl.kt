package com.zezekalo.pixabaytest.data.datasource.remote


import com.zezekalo.pixabaytest.data.BuildConfig
import com.zezekalo.pixabaytest.data.datasource.remote.entity.RemotePicture
import com.zezekalo.pixabaytest.domain.exception.DomainException
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RemotePictureDataSourceImpl @Inject constructor(
    private val apiService: IRetrofitApiService,
) : RemotePictureDataSource {

    override suspend fun getPictures(query: String): List<RemotePicture> =
        try {
            apiService.getPictures(
                key = BuildConfig.API_KEY,
                query = query,
                imageType = IMAGE_TYPE
            ).pictures
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }


    private fun mapToDomainException(exception: Exception): DomainException =
        when(exception) {
            is IOException -> DomainException.NoInternet()
            is HttpException -> DomainException.CommonHttp(
                code = exception.code(),
                errorMessage = exception.message()
            )
            else -> DomainException.Unexpected(cause = exception)
        }

    companion object {

        private const val IMAGE_TYPE = "photo"
    }
}