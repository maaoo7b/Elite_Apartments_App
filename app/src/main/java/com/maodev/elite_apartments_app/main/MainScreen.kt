package com.maodev.elite_apartments_app.main

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.state
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(selectedImageUri!!, flag)
            viewModel.onImgSelected(selectedImageUri.toString())
        }
    )
    Column(modifier = Modifier.fillMaxWidth()) {

        Button(onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
            viewModel.addImage()
        }) {
            Text(text = "OPEN GALLERY")
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.listUris) {
                AsyncImage(model = it.uri, contentDescription = null)
                Text(text = it.uri)
            }
        }

    }


}