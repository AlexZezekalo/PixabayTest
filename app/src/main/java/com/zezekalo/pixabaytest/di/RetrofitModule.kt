package com.zezekalo.pixabaytest.di

import com.zezekalo.pixabaytest.BuildConfig
import com.zezekalo.pixabaytest.data.datasource.remote.IRetrofitApiService
import com.zezekalo.pixabaytest.domain.di.HeaderInterceptor
import com.zezekalo.pixabaytest.domain.di.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @LoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor()
        .apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }

    @HeaderInterceptor
    @Provides
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        request.newBuilder()
            .addHeader(name = "accept", value = "application/json")
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @HeaderInterceptor headersInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .client(client)
            .baseUrl(com.zezekalo.pixabaytest.data.BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideRetrofitApiService(retrofit: Retrofit): IRetrofitApiService =
        retrofit.create(IRetrofitApiService::class.java)
}
