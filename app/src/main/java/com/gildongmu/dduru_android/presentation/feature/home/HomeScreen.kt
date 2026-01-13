package com.gildongmu.dduru_android.presentation.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.domain.entity.TravelRoomSummary
import com.gildongmu.dduru_android.presentation.feature.home.component.TravelRoomCard

@Composable
fun HomeScreen() {
    val roomList = getDemoTravelRooms()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DduruTheme.colors.Gray50),
        contentPadding = PaddingValues(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. 최상단 상태바 공간
        item {
            Spacer(modifier = Modifier.height(62.dp))
        }

        // 2. 환영멘트 및 검색 버튼 영역
        item {
            Row(
                modifier = Modifier
                    .width(343.dp)
                    .height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(70.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "안녕하세요!\n오늘은 어떤 여행을 찾으시나요?",
                    modifier = Modifier.width(225.dp),
                    style = DduruTheme.typography.HomeGreeting.copy(color = Color.Black)
                )
                HomeSearchButton()
            }
        }

        // 3. AI 추천 배너
        item {
            Spacer(modifier = Modifier.height(20.dp))
            AiRecommendBanner()
        }

        // 4. 필터 섹션
        item { HomeFilterSection() }

        // 5. 여행방 리스트
        items(roomList) { room ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                TravelRoomCard(
                    room = room,
                    onClick = {
                        // TODO: 네비게이션 추가
                        // onNavigateToDetail(room.id)
                    }
                )
            }
        }
    }
}

// --- UI 구성 요소 함수들 ---

@Composable
private fun HomeSearchButton() {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 16.dp,
                spotColor = DduruTheme.colors.BlackAlpha10,
                shape = RoundedCornerShape(99.dp)
            )
            .shadow(
                elevation = 8.dp,
                spotColor = DduruTheme.colors.BlackAlpha08,
                shape = RoundedCornerShape(99.dp)
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.WhiteAlpha30,
                shape = RoundedCornerShape(99.dp)
            )
            .size(48.dp)
            .background(
                color = DduruTheme.colors.WhiteAlpha50,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_md),
            contentDescription = "검색하기",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun AiRecommendBanner() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(59.dp)
            .shadow(
                elevation = 16.dp,
                spotColor = DduruTheme.colors.PrimaryShadow,
                shape = RoundedCornerShape(16.dp)
            )
            .background(DduruTheme.colors.Gray0, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sparkle),
                contentDescription = null,
                modifier = Modifier.size(19.dp)
            )
            Text(
                text = "AI 추천으로 동행 찾기",
                style = DduruTheme.typography.HomeBanner.copy(
                    color = DduruTheme.colors.Primary
                )
            )
        }
    }
}

@Composable
private fun HomeFilterSection() {
    LazyRow(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { HomeFilterChip(iconId = R.drawable.ic_filter, text = "필터", isSelected = false) }
        item { HomeFilterChip(iconId = R.drawable.ic_calendar, text = "2026년 12월", isSelected = true) }
        item { HomeFilterChip(iconId = R.drawable.ic_user, text = "남성", isSelected = true) }
    }
}

@Composable
private fun HomeFilterChip(iconId: Int, text: String, isSelected: Boolean) {
    val bgColor = if (isSelected) DduruTheme.colors.Primary else DduruTheme.colors.Gray100
    val textColor = if (isSelected) DduruTheme.colors.Gray0 else DduruTheme.colors.Gray700

    Row(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(99.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = text,
            style = DduruTheme.typography.HomeFilter.copy(color = textColor)
        )
        if (isSelected) {
            Image(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

// --- 데모 데이터 (기본값 사용) ---
private fun getDemoTravelRooms() = listOf(
    TravelRoomSummary(
        id = 1L,
        title = "제주 20일-23일 동행구해요",
        isRecruitOpen = true,
        daysLeft = 6,
        startDate = "2025.12.20",
        endDate = "2025.12.23",
        destination = "제주",
        photoUrls = null,
        viewCount = 123,        // ✅ 추가
        likeCount = 45,         // ✅ 추가
        recruitCapacity = 4,
        recruitCount = 1,
        preferredGender = "M",
        budgetMin = 40,
        budgetMax = 100,
        tags = null,
        isSimilar = true
    ),
    TravelRoomSummary(
        id = 2L,
        title = "강릉 맛집 투어 동행",
        isRecruitOpen = true,
        daysLeft = 10,
        startDate = "2025.12.25",
        endDate = "2025.12.27",
        destination = "강원",
        photoUrls = null,
        viewCount = 89,         // ✅ 추가
        likeCount = 23,         // ✅ 추가
        recruitCapacity = 2,
        recruitCount = 1,
        preferredGender = "U",
        budgetMin = null,
        budgetMax = null,
        tags = null,
        isSimilar = false
    ),
    TravelRoomSummary(
        id = 3L,
        title = "부산 야경 동행 찾아요",
        isRecruitOpen = true,
        daysLeft = 2,
        startDate = "2025.12.28",
        endDate = "2025.12.30",
        destination = "부산",
        photoUrls = null,
        viewCount = 201,        // ✅ 추가
        likeCount = 67,         // ✅ 추가
        recruitCapacity = 3,
        recruitCount = 2,
        preferredGender = "F",
        budgetMin = 15,
        budgetMax = 30,
        tags = null,
        isSimilar = true
    )
)

@Preview(showBackground = true, device = "spec:width=375dp,height=812dp")
@Composable
fun HomeScreenPreview() {
    DduruTheme {
        HomeScreen()
    }
}