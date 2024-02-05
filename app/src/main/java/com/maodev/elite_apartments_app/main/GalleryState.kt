package com.maodev.elite_apartments_app.main
data class GalleryState(
    val uri: String = "",
    val dateUploaded: String = "",
    val listUris: List<Gallery> = emptyList()
)
