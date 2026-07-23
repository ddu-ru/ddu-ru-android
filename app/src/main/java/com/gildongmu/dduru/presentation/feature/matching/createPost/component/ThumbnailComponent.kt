package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruHazeBackground
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.common.extension.rememberDduruHazeState
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@Composable
fun ThumbnailImageCard(
    regionName: String,
    countryName: String,
    imageUrl: String?,
    imageResId: Int,
    modifier: Modifier = Modifier,
    onChangeClick: () -> Unit = {}
) {
    val hazeState = rememberDduruHazeState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(344f / 130f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .dduruHazeBackground(hazeState),
            contentScale = ContentScale.Crop
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(hazeState),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(hazeState),
                contentScale = ContentScale.Crop

            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = DduruTheme.colors.black.copy(alpha = 0.3f)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = regionName,
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray0
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = countryName,
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray0.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.weight(1f))

            TransparentButton(
                text = "사진 변경",
                hazeState = hazeState,
                onClick = onChangeClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ThumbnailImageCardPreview() {
    DduruTheme {
        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.padding(24.dp)
        ) {
            ThumbnailImageCard(
                regionName = "후쿠오카",
                countryName = "일본",
                imageResId = R.drawable.fukuoka,
                onChangeClick = {}
            )
        }
    }
}

@Composable
private fun transparentButtonHazeStyle(): HazeStyle = HazeStyle(
    tint = DduruTheme.colors.gray50.copy(alpha = 0.22f),
    blurRadius = 8.dp,
    noiseFactor = 0f
)

@Composable
fun TransparentButton(
    text: String,
    hazeState: HazeState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
) {
    val containerShape = RoundedCornerShape(99.dp)
    val clickableModifier = if (enabled) {
        Modifier.noRippleClickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .border(
                width = 0.8.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.2f),
                shape = containerShape
            )
            .then(clickableModifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .hazeChild(
                    state = hazeState,
                    shape = containerShape,
                    style = transparentButtonHazeStyle()
                )
                .background(
                    color = DduruTheme.colors.gray0.copy(alpha = 0.10f),
                    shape = containerShape
                )
        )

        Text(
            text = text,
            modifier = Modifier.padding(contentPadding),
            style = DduruTheme.typography.Micro,
            color = DduruTheme.colors.gray0
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransparentButtonPreview() {
    val hazeState = rememberDduruHazeState()
    DduruTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFF5A6F95))
                    .dduruHazeBackground(hazeState)
            )

            TransparentButton(
                text = "사진 변경",
                hazeState = hazeState,
                onClick = {}
            )
        }
    }
}
