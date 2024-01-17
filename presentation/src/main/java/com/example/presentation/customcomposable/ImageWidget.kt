package com.example.presentation.customcomposable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ImageWidget(
    imageURL: String,
    contentDescription: String? = null,
    modifier: Modifier,
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .crossfade(true)
                .build(),
        ),
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
