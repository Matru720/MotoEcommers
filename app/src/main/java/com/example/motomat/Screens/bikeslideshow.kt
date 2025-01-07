package com.example.motomat.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay


@Composable
fun BikeSlideshow() {
    val images = listOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8QC-ND8LTGoZ2NgRZ2pia_ZzSQ7KFwMyORA&s", // Replace with actual image URLs
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUCHLzsZ6pfblXGxrj058LEjRj4R0vMkHVuA&s",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3c-xs6XIiKEV5JoXLjfMwpVXQieXX1VNIig&s"
    )
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Change image every 3 seconds
            currentIndex = (currentIndex + 1) % images.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(images[currentIndex]),
            contentDescription = "Slideshow Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}