package com.zezekalo.pixabaytest.presentation.entity

data class UiPicture(
    val id: Int,
    val user: String,
    val tags: List<String>,
    val thumbnailUrl: String,
) {
    fun tagsAsString() = tags.joinToString()
}
