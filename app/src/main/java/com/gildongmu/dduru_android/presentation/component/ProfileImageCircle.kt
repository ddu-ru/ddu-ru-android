package com.gildongmu.dduru_android.presentation.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileImageCrop(
    modifier: Modifier = Modifier,
    resId: Int? = null,
    imageUri: Uri? = null,
    scale: Float = 1f,
    offsetRatio: Float = 0f,
    size: Dp = 100.dp
){
    Box(
        modifier = modifier
            .background(Color(0xFFACD3FF), CircleShape)
            .size(size)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val image = imageUri ?: resId

        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .scale(scale)
                .offset{
                    IntOffset(x = 0, y = (size.toPx() * offsetRatio).toInt())
                }
        )
    }
}
