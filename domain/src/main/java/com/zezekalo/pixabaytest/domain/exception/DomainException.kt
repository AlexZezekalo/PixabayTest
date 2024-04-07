package com.zezekalo.pixabaytest.domain.exception

sealed class DomainException(
    override val message: String
) : RuntimeException(message) {

    data class NoInternet(
        override val message: String = "Internet exception"
    ) : DomainException(message)

    data class Unexpected(
        override val message: String = "Unexpected exception",
        override val cause: Exception
    ) : DomainException(message)

    data class CommonHttp(
        override val message: String = "Retrofit Http Exception",
        val code: Int,
        val errorMessage: String,
    ) : DomainException(message)

    data class PictureNotFound(
        override val message: String = "Picture data isn't found in cache"
    ) : DomainException(message)
}