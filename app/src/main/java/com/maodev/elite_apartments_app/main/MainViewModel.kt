package com.maodev.elite_apartments_app.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maodev.elite_apartments_app.data.GalleryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val galleryRepository: GalleryRepository) : ViewModel() {

    var state by mutableStateOf(GalleryState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(listUris = galleryRepository.getImages())
        }
    }

    fun onImgSelected(uri: String) {
        state = state.copy(uri = uri)
    }

    fun addImage() {
        val gallery = Gallery(uri = state.uri, date = state.dateUploaded)
        viewModelScope.launch {
            galleryRepository.addImage(gallery)
        }
    }
}