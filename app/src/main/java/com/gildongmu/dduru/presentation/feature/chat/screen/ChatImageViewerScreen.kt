package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private const val MIN_SCALE = 1f
private const val MAX_SCALE = 4f

@Composable
fun ChatImageViewerScreen(
    imageUrls: List<String>,
    initialIndex: Int,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = initialIndex.coerceIn(0, (imageUrls.size - 1).coerceAtLeast(0))
    ) { imageUrls.size }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ZoomableImage(imageUrl = imageUrls[page])
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .noRippleClickable(onClick = onCloseClick)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_x_gray),
                    contentDescription = "닫기",
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(24.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                if (imageUrls.size > 1) {
                    Text(
                        text = "${pagerState.currentPage + 1} / ${imageUrls.size}",
                        style = DduruTheme.typography.Title3,
                        color = DduruTheme.colors.gray0
                    )
                }
            }
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun ZoomableImage(imageUrl: String) {
    var scale by remember { mutableFloatStateOf(MIN_SCALE) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(MIN_SCALE, MAX_SCALE)
        offset = if (scale > MIN_SCALE) offset + panChange else Offset.Zero
    }

    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize()
            .transformable(
                state = transformableState,
                canPan = { scale > MIN_SCALE }
            )
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offset.x
                translationY = offset.y
            }
    )
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatImageViewerScreenPreview() {
    DduruTheme {
        ChatImageViewerScreen(
            imageUrls = List(3) { "" },
            initialIndex = 1,
            onCloseClick = {}
        )
    }
}
