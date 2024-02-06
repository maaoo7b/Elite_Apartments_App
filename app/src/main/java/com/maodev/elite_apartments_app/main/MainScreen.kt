package com.maodev.elite_apartments_app.main

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.state
    Scaffold(
        topBar = { MyTopAppBar() },
        floatingActionButton = { MyFAB(viewModel) })
    {
        Column(modifier = Modifier.padding(it)) {
            viewModel.getImages()
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.listUris) { image ->
                    ItemGallery(image, viewModel)
                }
            }
        }
    }
}

@Composable
fun MyFAB(viewModel: MainViewModel) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedImageUri = uri
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(selectedImageUri!!, flag)
                viewModel.onImgSelected(selectedImageUri.toString())
                viewModel.addImage()
            }
        )

    FloatingActionButton(
        onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }, modifier = Modifier.padding(16.dp),
        containerColor = Color(0xFF8BC34A)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemGallery(image: Gallery, viewModel: MainViewModel) {
    val context = LocalContext.current
    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = {
                    viewModel.deleteImage(image.id)
                    viewModel.getImages()
                    Toast
                        .makeText(context, "Image deleted", Toast.LENGTH_SHORT)
                        .show()
                })
            },
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EBE6)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFECE5E5))
        ) {
            AsyncImage(
                model = image.uri,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Uploaded: ",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = image.date,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Elite Apartments App Gallery",
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color(0xFF8BC34A)
        )
    )
}
