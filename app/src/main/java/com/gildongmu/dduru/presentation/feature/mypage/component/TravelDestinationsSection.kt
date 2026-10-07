package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class TravelDestination(
    val name: String,
    val imageUrl: Any
)

@Composable
fun TravelDestinationsSection(
    destinations: List<TravelDestination>,
    modifier: Modifier = Modifier,
    label: String = "선호 여행지"
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            color = DduruTheme.colors.gray400,
            style = DduruTheme.typography.Caption2
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            destinations.forEach { destination ->
                StatusTagXs(
                    text = destination.name,
                    textStyle = DduruTheme.typography.Caption1,
                    contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                    contentSpacing = 8.dp,
                    leadingContent = {
                        AsyncImage(
                            model = destination.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                        )
                    }
                )
            }
        }
    }
}

@Preview(name = "TravelDestinationsSection")
@Composable
private fun TravelDestinationsSectionPreview() {
    DduruTheme {
        TravelDestinationsSection(
            destinations = listOf(
                TravelDestination(name = "도쿄", imageUrl = ""),
                TravelDestination(name = "파리", imageUrl = ""),
                TravelDestination(name = "제주도", imageUrl = "")
            )
        )
    }
}
