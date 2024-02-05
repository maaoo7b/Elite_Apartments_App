package com.maodev.elite_apartments_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GalleryEntity::class], version = 1)
abstract class GalleryDataBase : RoomDatabase() {
    abstract val dao: GalleryDAO
}