package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.tripgroup.creative.component.TransparentButton
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun ThumbnailImageCard(
    regionName: String,
    countryName: String,
    imageResId: Int,
    modifier: Modifier = Modifier,
    onChangeClick: () -> Unit = {}
) {
    val hazeState = remember { HazeState() }

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
                .haze(hazeState),
            contentScale = ContentScale.Crop

        )

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
