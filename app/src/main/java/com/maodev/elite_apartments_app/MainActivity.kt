package com.maodev.elite_apartments_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.maodev.elite_apartments_app.data.GalleryDataBase
import com.maodev.elite_apartments_app.data.GalleryRepository
import com.maodev.elite_apartments_app.main.MainScreen
import com.maodev.elite_apartments_app.main.MainViewModel
import com.maodev.elite_apartments_app.ui.theme.Elite_Apartments_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Room.databaseBuilder(this, GalleryDataBase::class.java, "gallery_db").build()
        val dao = db.dao
        val repository = GalleryRepository(dao)
        val viewModel = MainViewModel(repository)
        super.onCreate(savedInstanceState)
        setContent {
            Elite_Apartments_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainScreen(viewModel)
                }
            }
        }
    }
}
