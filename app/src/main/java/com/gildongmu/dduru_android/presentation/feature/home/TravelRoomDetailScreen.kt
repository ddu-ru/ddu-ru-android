package com.gildongmu.dduru_android.presentation.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.component.*
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.domain.entity.Author
import com.gildongmu.dduru_android.domain.entity.TravelRoomDetail

@Composable
fun TravelRoomDetailScreen(
    roomDetail: TravelRoomDetail,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DduruTheme.colors.Gray50)
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 94.dp)
            ) {
                DduruButton(
                    text = "동행 신청하기",
                    onClick = onApplyClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DduruTheme.colors.Gray50)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }

                // 1. 이미지 헤더
                item {
                    DetailImageHeader(
                        destination = roomDetail.destination,
                        country = roomDetail.getCountryName(), // 계산된 값 사용
                        imageUrl = roomDetail.photoUrls?.firstOrNull(),
                        imageRes = R.drawable.img_container
                    )
                }

                // 2. 제목 및 요약 정보
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = roomDetail.title,
                            style = DduruTheme.typography.TitleSmall.copy(
                                fontSize = 20.sp,
                                color = DduruTheme.colors.Gray868
                            )
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            DetailSummaryRow(
                                R.drawable.ic_calendar,
                                "${roomDetail.startDate} - ${roomDetail.endDate} ${roomDetail.getDurationText()}"
                            )
                            DetailSummaryRow(
                                R.drawable.ic_marker_pin,
                                "${roomDetail.getCountryName()} | ${roomDetail.destination}"
                            )
                            DetailSummaryRow(
                                R.drawable.ic_info_circle,
                                "${roomDetail.preferredGender} · ${roomDetail.budgetMin ?: "협의"}~${roomDetail.budgetMax ?: "협의"}만원"
                            )
                        }
                    }
                }

                // 3. 설명
                item {
                    DetailSectionCard(
                        title = "설명",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = roomDetail.content,
                            style = DduruTheme.typography.BodySmall.copy(
                                color = DduruTheme.colors.Gray800
                            )
                        )
                    }

                    // 태그가 있으면 표시
                    roomDetail.tags?.takeIf { it.isNotEmpty() }?.let { tags ->
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            tags.take(3).forEach { tag ->
                                DetailSmallBadge(text = "#$tag", isPrimary = false)
                            }
                        }
                    }
                }

                // 4. 함께하는 사람들
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailParticipantSection(
                        currentCount = roomDetail.recruitCount,
                        maxCount = roomDetail.recruitCapacity,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        DetailParticipantItem(
                            nickname = roomDetail.author.nickname,
                            travelType = "", // API에 없음 - 추후 추가
                            age = roomDetail.author.getAge(),
                            gender = roomDetail.author.gender,
                            isHost = true
                        )
                    }
                }

                // 5. 모집 정보
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DetailHalfInfoCard(
                            R.drawable.ic_users,
                            "모집 인원",
                            "${roomDetail.recruitCapacity}명",
                            Modifier.weight(1f)
                        )
                        DetailHalfInfoCard(
                            R.drawable.ic_calendar_dots,
                            "모집 기간",
                            roomDetail.getRecruitPeriodDays(),
                            Modifier.weight(1f),
                            roomDetail.getDaysLeftBadge()
                        )
                    }
                }

                // 6. 날짜 박스
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailDateRangeBox(
                        startDate = roomDetail.startDate,
                        endDate = roomDetail.endDate,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // 7. 캘린더 자리 (추후 구현)
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(438.dp)
                    )
                }

                // 8. 추가 상세 조건 (성별/연령대)
                item {
                    DetailSectionCard(
                        title = "",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PreferenceItem(
                                iconId = R.drawable.ic_user_black,
                                label = "함께하고 싶은 성별",
                                value = roomDetail.preferredGender
                            )
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(38.dp)
                                    .background(DduruTheme.colors.Gray200)
                            )
                            PreferenceItem(
                                iconId = R.drawable.ic_hash,
                                label = "함께하고 싶은 연령대",
                                value = roomDetail.getPreferredAgeRange()
                            )
                        }
                    }
                }

                // 9. 예상 경비
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailBudgetSection(
                        minBudget = roomDetail.budgetMin,
                        maxBudget = roomDetail.budgetMax,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // --- 상단 플로팅 버튼 영역 ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailRoundButton(iconId = R.drawable.ic_chevron_left, onClick = onBackClick)
                DetailRoundButton(iconId = R.drawable.ic_dots_vertical, onClick = onMoreClick)
            }
        }
    }
}

// --- 보조 컴포넌트들 ---

@Composable
private fun PreferenceItem(iconId: Int, label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = label,
                style = DduruTheme.typography.HomeFilter.copy(
                    color = DduruTheme.colors.Gray700
                )
            )
        }
        Text(
            text = value,
            style = DduruTheme.typography.LabelLarge.copy(
                color = DduruTheme.colors.Gray800,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun DetailSummaryRow(iconId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = text,
            style = DduruTheme.typography.BodySmall.copy(
                color = DduruTheme.colors.Gray600
            )
        )
    }
}

// --- 프리뷰 데이터 ---
@Preview(showBackground = true, device = "spec:width=375dp,height=1651dp")
@Composable
fun TravelRoomDetailScreenPreview() {
    val dummyAuthor = Author(
        id = 1L,
        name = "김철수",
        nickname = "겨울이오길",
        profileImage = null,
        gender = "남성",
        birthday = "1990-01-01"
    )

    val dummyDetail = TravelRoomDetail(
        id = 1L,
        title = "제주 20일-23일 동행구해요",
        content = "제주도에서 힐링 여행 함께 하실 분 구합니다. 렌트카는 제가 준비했고, 숙소는 게스트하우스 예약했어요!",
        startDate = "2025-12-20",
        endDate = "2025-12-23",
        recruitDeadline = "2025-12-15",
        createdAt = "2025-11-01T12:00:00Z",
        destination = "제주",
        destinationId = 1L,
        isRecruitOpen = true,
        recruitCapacity = 4,
        recruitCount = 1,
        daysLeft = 6,
        preferredGender = "남성",
        preferredAgeMin = "20s",
        preferredAgeMax = "30s",
        budgetMin = 50,
        budgetMax = 100,
        photoUrls = null,
        tags = listOf("힐링", "맛집투어", "드라이브"),
        viewCount = 123,
        likeCount = 45,
        status = "OPEN",
        author = dummyAuthor
    )

    DduruTheme {
        TravelRoomDetailScreen(
            roomDetail = dummyDetail,
            onBackClick = {},
            onMoreClick = {},
            onApplyClick = {}
        )
    }
}