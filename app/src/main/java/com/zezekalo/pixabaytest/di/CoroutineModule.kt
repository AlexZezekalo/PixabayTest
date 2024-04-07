package com.zezekalo.pixabaytest.di

import com.zezekalo.pixabaytest.domain.common.PresentationDataDelegate
import com.zezekalo.pixabaytest.domain.di.Default
import com.zezekalo.pixabaytest.domain.di.Foreground
import com.zezekalo.pixabaytest.domain.di.Io
import com.zezekalo.pixabaytest.presentation.util.getSafeCoroutineExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

@InstallIn(ViewModelComponent::class)
@Module
class CoroutineModule {

    @Provides
    @Default
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Io
    fun provideMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Foreground
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providePresentationDataDelegate(
        @Default coroutineDispatcher: CoroutineDispatcher
    ): PresentationDataDelegate = object : PresentationDataDelegate {

        override val backgroundDispatcher: CoroutineDispatcher
            get() = coroutineDispatcher
        override val coroutineExceptionHandler: CoroutineExceptionHandler
            get() = getSafeCoroutineExceptionHandler()
    }
}