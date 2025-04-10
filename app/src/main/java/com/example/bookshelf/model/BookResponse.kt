package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(val items: List<BookItem> = emptyList())

@Serializable
data class BookItem(val id: String, val volumeInfo: VolumeInfo)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String> = emptyList(),
    val description: String? = null,
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
    val extraLarge: String? = null
)