package com.zezekalo.pixabaytest.domain.repository

import com.zezekalo.pixabaytest.domain.entity.Picture
import kotlinx.coroutines.flow.Flow


interface PictureRepository {

    val picturesFlow: Flow<List<Picture>>
}