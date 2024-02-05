package com.maodev.elite_apartments_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GalleryEntity(
    @PrimaryKey
    val uri: String,
    val date: String

)
