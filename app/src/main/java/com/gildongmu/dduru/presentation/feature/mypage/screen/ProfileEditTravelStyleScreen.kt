package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.mypage.component.ProfileEditHeader
import com.gildongmu.dduru.presentation.feature.mypage.component.ProfileEditTabRow
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleDescriptionBox
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleTagChip
import com.gildongmu.dduru.presentation.feature.mypage.model.TravelStyleTag

private const val TRAVEL_STYLE_TAB_INDEX = 2

@Composable
fun ProfileEditTravelStyleScreen(
    styleName: String,
    bubbleMessage: String,
    imageUrl: String,
    tags: List<TravelStyleTag>,
    descriptions: List<String>,
    onBackClick: () -> Unit,
    onTabClick: (Int) -> Unit,
    onTagToggle: (Int) -> Unit,
    onRetestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        ProfileEditHeader(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            ProfileEditTabRow(
                selectedIndex = TRAVEL_STYLE_TAB_INDEX,
                onTabClick = onTabClick,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(37.dp))

            SpeechBubble(
                modifier = Modifier.width(239.dp),
                arrowWidth = 12.dp,
                arrowHeight = 12.dp,
                shadowElevation = 8.dp
            ) {
                Text(
                    text = bubbleMessage,
                    style = DduruTheme.typography.Body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
                )
            }

            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_travel_style_result),
                error = painterResource(id = R.drawable.img_travel_style_result),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(192.dp)
                    .aspectRatio(192f / 214f)
            )

            Text(
                text = styleName,
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.forEachIndexed { index, tag ->
                    TravelStyleTagChip(
                        text = tag.text,
                        isActive = tag.isActive,
                        onToggleClick = { onTagToggle(index) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TravelStyleDescriptionBox(items = descriptions)

            Spacer(modifier = Modifier.height(32.dp))

            DduruButton(
                text = "테스트 다시하기",
                onClick = onRetestClick,
                textStyle = DduruTheme.typography.H1,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.height(93.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 882, name = "36400 나의 여행 스타일")
@Composable
private fun ProfileEditTravelStyleScreenPreview() {
    DduruTheme {
        ProfileEditTravelStyleScreen(
            styleName = "스윗",
            bubbleMessage = "모두가 편안해야\n내가 편해요!",
            imageUrl = "",
            tags = listOf(
                TravelStyleTag(emoji = "🍜", label = "맛집러"),
                TravelStyleTag(emoji = "📸", label = "기록파"),
                TravelStyleTag(emoji = "🌃", label = "야경좋아함", isActive = false)
            ),
            descriptions = listOf("어쩌구 저쩌구 설명..", "이러쿵 저러쿵 설명.."),
            onBackClick = {},
            onTabClick = {},
            onTagToggle = {},
            onRetestClick = {}
        )
    }
}
