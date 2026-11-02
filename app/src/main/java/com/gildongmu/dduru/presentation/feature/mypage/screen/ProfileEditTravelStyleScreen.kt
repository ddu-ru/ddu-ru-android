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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.mypage.component.ProfileEditHeader
import com.gildongmu.dduru.presentation.feature.mypage.component.ProfileEditTabRow
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleDescriptionBox
import com.gildongmu.dduru.presentation.feature.mypage.component.TravelStyleTagChip
import com.gildongmu.dduru.presentation.feature.mypage.model.TravelStyleTag
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.ProfileEditTravelStyleViewModel

private const val TRAVEL_STYLE_TAB_INDEX = 2

@Composable
fun ProfileEditTravelStyleRoute(
    onBackClick: () -> Unit,
    onTabClick: (Int) -> Unit,
    onRetestClick: () -> Unit,
    onNoSurveyResult: () -> Unit,
    viewModel: ProfileEditTravelStyleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.loadTravelStyle()
    }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Empty) onNoSurveyResult()
    }

    when (val state = uiState) {
        UiState.Loading, UiState.Empty -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "여행 스타일을 불러오지 못했어요.")
        is UiState.Success -> ProfileEditTravelStyleScreen(
            styleName = state.data.styleName,
            bubbleMessage = state.data.bubbleMessage,
            imageUrl = state.data.imageUrl,
            tags = state.data.tags,
            descriptions = state.data.descriptions,
            onBackClick = onBackClick,
            onTabClick = onTabClick,
            onTagToggle = viewModel::onTagToggle,
            onRetestClick = onRetestClick,
            canRetake = state.data.canRetake
        )
    }
}

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
    modifier: Modifier = Modifier,
    canRetake: Boolean = true
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
                        text = tag.text.trim(),
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
                enabled = canRetake,
                disabledColors = DduruButtonDisabledColors(
                    backgroundColor = DduruTheme.colors.gray200,
                    textColor = DduruTheme.colors.gray400,
                    borderColor = DduruTheme.colors.gray200
                ),
                textStyle = DduruTheme.typography.H1,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            if (!canRetake) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "여행 스타일은 마지막 변경일로부터 30일이 지나야 다시 수정할 수 있어요",
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

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
