package com.zezekalo.pixabaytest.domain.mapper

interface SimpleMapper<T, R> {

    fun map(item: T): R
}