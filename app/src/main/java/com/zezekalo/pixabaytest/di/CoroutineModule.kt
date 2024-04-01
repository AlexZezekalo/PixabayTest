package com.zezekalo.pixabaytest.di

import com.zezekalo.pixabaytest.domain.di.Background
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(ViewModelComponent::class)
@Module
class CoroutineModule {

    @Provides
    @Background
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun provideCoroutineScope(
        dispatcher: CoroutineDispatcher,
        coroutineExceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope =
        CoroutineScope(SupervisorJob() + dispatcher + coroutineExceptionHandler)

    @Provides
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
}