package com.gildongmu.dduru_android.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.domain.entity.TravelRoomSummary

@Composable
fun TravelRoomCard(
    room: TravelRoomSummary,
    onClick: () -> Unit = {},  // ✅ 클릭 이벤트 추가
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(343.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DduruTheme.colors.BlackAlpha10
            )
            .background(color = DduruTheme.colors.Gray0, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)  // ✅ 클릭 가능하게
    ) {
        // --- 1. 이미지 영역 ---
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        ) {
            // TODO: 나중에 room.photoUrls?.firstOrNull()로 Coil 이미지 로드
            Image(
                painter = painterResource(id = R.drawable.img_container),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop  // ✅ FillBounds → Crop
            )

            if (room.isSimilar) {
                BadgeSimilar()
            }
        }

        // --- 2. 정보 영역 ---
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusTag(
                    text = if (room.isRecruitOpen) "모집중" else "모집마감",
                    isFilled = false
                )
                StatusTag(
                    text = "D-${room.daysLeft}",
                    isFilled = true
                )
            }

            Text(
                text = room.title,
                style = DduruTheme.typography.LabelLarge.copy(
                    color = DduruTheme.colors.Gray868
                )
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                InfoRow(
                    iconId = R.drawable.ic_calendar,
                    text = "${room.startDate} ~ ${room.endDate}"
                )
                InfoRow(
                    iconId = R.drawable.ic_marker_pin,
                    text = room.getLocationText()  // ✅ 수정
                )

                val budgetText = if (room.budgetMin != null && room.budgetMax != null) {
                    "${room.budgetMin}~${room.budgetMax}만원"
                } else {
                    "예산협의"
                }

                InfoRow(
                    iconId = R.drawable.ic_info_circle,
                    text = "${room.getGenderText()} · $budgetText"  // ✅ 수정
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ParticipantAvatars()
                Text(
                    text = "${room.recruitCount}/${room.recruitCapacity}명",
                    style = DduruTheme.typography.BodyMedium.copy(
                        color = DduruTheme.colors.GrayText400
                    )
                )
            }
        }
    }
}

// --- 보조 컴포넌트들 ---

@Composable
private fun StatusTag(text: String, isFilled: Boolean) {
    val bgColor = if (isFilled) DduruTheme.colors.Primary else DduruTheme.colors.Gray0
    val textColor = if (isFilled) DduruTheme.colors.Gray0 else DduruTheme.colors.Primary

    Box(
        modifier = Modifier
            .background(color = bgColor, shape = RoundedCornerShape(4.dp))
            .then(
                if (!isFilled) {
                    Modifier.border(1.dp, DduruTheme.colors.Primary, RoundedCornerShape(4.dp))
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.BodySmall.copy(
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun InfoRow(iconId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = text,
            style = DduruTheme.typography.BodySmall.copy(
                color = DduruTheme.colors.Gray600
            )
        )
    }
}

@Composable
private fun ParticipantAvatars() {
    Row(horizontalArrangement = Arrangement.spacedBy((-4).dp)) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(DduruTheme.colors.BlueProfile1, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(DduruTheme.colors.BlueProfile2, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(DduruTheme.colors.Gray0, CircleShape)
                .border(1.dp, DduruTheme.colors.Primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
private fun BadgeSimilar() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = DduruTheme.colors.BlackAlpha10,
                shape = RoundedCornerShape(99.dp)
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.WhiteAlpha05,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "⭐ 비슷한 성향",
            style = DduruTheme.typography.LabelSmall.copy(
                fontSize = 10.sp,
                color = DduruTheme.colors.Gray0
            )
        )
    }
}