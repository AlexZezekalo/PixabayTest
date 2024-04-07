package com.zezekalo.pixabaytest.domain.di

import javax.inject.Qualifier


@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Io

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Default


@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Foreground