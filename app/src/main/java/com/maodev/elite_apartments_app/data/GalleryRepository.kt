package com.maodev.elite_apartments_app.data

import com.maodev.elite_apartments_app.main.Gallery

class GalleryRepository(private val galleryDAO: GalleryDAO) {

    suspend fun getImages(): List<Gallery> {
        val entities = galleryDAO.getImages()
        return entities.map {
            Gallery(uri = it.uri, date = it.date)
        }
    }

    suspend fun addImage(image: Gallery) {
        val entity = GalleryEntity(uri = image.uri, date = image.date)
        galleryDAO.addImage(entity)
    }
}