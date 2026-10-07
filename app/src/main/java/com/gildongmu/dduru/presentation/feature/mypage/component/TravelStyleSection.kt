package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class TravelStyleTag(
    val emoji: String,
    val label: String
)

@Composable
fun TravelStyleSection(
    title: String,
    description: String,
    tags: List<TravelStyleTag>,
    modifier: Modifier = Modifier,
    label: String = "나의 여행 스타일",
    onExpandClick: (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray100)

        Column(
            modifier = Modifier.padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                color = DduruTheme.colors.gray400,
                style = DduruTheme.typography.Caption2
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (onExpandClick != null) {
                                Modifier.clickable(onClick = onExpandClick)
                            } else {
                                Modifier
                            }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = title,
                            color = DduruTheme.colors.gray868,
                            style = DduruTheme.typography.Title2
                        )
                        Text(
                            text = description,
                            color = DduruTheme.colors.gray500,
                            style = DduruTheme.typography.Caption2
                        )
                    }

                    if (onExpandClick != null) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_right),
                            contentDescription = null,
                            tint = DduruTheme.colors.gray600,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tags.forEach { tag ->
                        StatusTagXs(
                            text = tag.label,
                            textStyle = DduruTheme.typography.Caption1,
                            contentPadding = PaddingValues(8.dp),
                            contentSpacing = 8.dp,
                            leadingContent = {
                                Text(
                                    text = tag.emoji,
                                    style = DduruTheme.typography.Body4
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "TravelStyleSection")
@Composable
private fun TravelStyleSectionPreview() {
    DduruTheme {
        TravelStyleSection(
            title = "스윗",
            description = "도시를 걸으며 새로운 장소를 발견해요!",
            tags = listOf(
                TravelStyleTag(emoji = "🍜", label = "맛집러"),
                TravelStyleTag(emoji = "📸", label = "기록파"),
                TravelStyleTag(emoji = "🌃", label = "야경 좋아함")
            ),
            onExpandClick = {}
        )
    }
}
