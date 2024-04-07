package com.zezekalo.pixabaytest.presentation.entity

data class UiPictureDetails(
    val id: Int,
    val user: String,
    val tags: List<String>,
    val bigImageUrl: String,
    val likeCount: Int,
    val downloadCount: Int,
    val commentCount: Int
) {
    fun tagsAsString() = tags.joinToString()
}