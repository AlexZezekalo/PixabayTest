package com.zezekalo.pixabaytest.domain.entity

data class Picture(
    val id: Int,
    val user: String,
    val tags: List<String>,
    val thumbnailUrl: String,
    val bigImageUrl: String,
    val likeCount: Int,
    val downloadCount: Int,
    val commentCount: Int
)