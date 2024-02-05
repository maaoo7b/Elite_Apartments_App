package com.maodev.elite_apartments_app.main

import java.time.LocalDate

data class GalleryState(
    val uri: String = "",
    val dateUploaded: String = LocalDate.now().toString(),
    val listUris: List<Gallery> = emptyList()
)
