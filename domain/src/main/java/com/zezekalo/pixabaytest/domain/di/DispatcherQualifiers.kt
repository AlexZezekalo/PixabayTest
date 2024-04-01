package com.zezekalo.pixabaytest.domain.di

import javax.inject.Qualifier


@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Background

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Foreground