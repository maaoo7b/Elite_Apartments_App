package com.maodev.elite_apartments_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GalleryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(galleryEntity: GalleryEntity)

    @Delete()
    suspend fun deleteImage(galleryEntity: GalleryEntity)

    @Query("SELECT * FROM GalleryEntity")
    suspend fun getImages(): List<GalleryEntity>
}