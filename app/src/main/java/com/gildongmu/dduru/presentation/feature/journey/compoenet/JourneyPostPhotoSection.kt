package com.gildongmu.dduru.presentation.feature.journey.compoenet

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.MAX_PHOTO_COUNT

@Composable
fun JourneyPostPhotoSection(
    photos: List<Uri>,
    onAddPhotoClick: () -> Unit,
    onRemovePhotoClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (photos.size < MAX_PHOTO_COUNT) {
            item {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DduruTheme.colors.gray100)
                        .noRippleClickable(onClick = onAddPhotoClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus_gray),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray400,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        itemsIndexed(photos) { index, uri ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(20.dp)
                        .background(DduruTheme.colors.black.copy(alpha = 0.5f), CircleShape)
                        .noRippleClickable { onRemovePhotoClick(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "사진 삭제",
                        modifier = Modifier.size(12.dp),
                        tint = DduruTheme.colors.gray0
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyPostPhotoSectionPreview() {
    DduruTheme {
        JourneyPostPhotoSection(photos = emptyList(), onAddPhotoClick = {}, onRemovePhotoClick = {})
    }
}
