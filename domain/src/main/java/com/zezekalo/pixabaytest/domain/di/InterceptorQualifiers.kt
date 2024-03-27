package com.zezekalo.pixabaytest.domain.di

import javax.inject.Qualifier

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptor