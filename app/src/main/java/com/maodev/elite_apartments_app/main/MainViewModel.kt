package com.maodev.elite_apartments_app.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maodev.elite_apartments_app.data.GalleryRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(private val galleryRepository: GalleryRepository) : ViewModel() {

    var state by mutableStateOf(GalleryState())
        private set

    fun getImages() {
        viewModelScope.launch {
            state = state.copy(listUris = galleryRepository.getImages())
        }
    }

    fun onImgSelected(uri: String) {
        state = state.copy(uri = uri)
    }

    fun addImage() {
        val gallery = Gallery(
            uri = state.uri, date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString()
        )
        viewModelScope.launch {
            galleryRepository.addImage(gallery)
        }
    }

    fun deleteImage(id: Int?) {
        viewModelScope.launch {
            galleryRepository.deleteImage(id)
        }
    }
}