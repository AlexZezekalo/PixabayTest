package com.zezekalo.pixabaytest.domain.di

import javax.inject.Qualifier


@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Remote

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Local