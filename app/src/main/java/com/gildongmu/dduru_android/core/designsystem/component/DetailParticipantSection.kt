package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailParticipantSection(
    currentCount: Int,
    maxCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit // 참여자 아이템 리스트 주입
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.Gray100, RoundedCornerShape(16.dp))
            .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // 1. 헤더 (제목 + 인원수)
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "함께하는 사람들",
                style = DduruTheme.typography.HomeFilter.copy(fontWeight = FontWeight.Bold, color = DduruTheme.colors.Gray800)
            )
            Text(
                text = "$currentCount / ${maxCount}명",
                style = DduruTheme.typography.HomeFilter.copy(color = DduruTheme.colors.Gray800)
            )
        }

        // 2. 참여자 아이템들이 쌓이는 영역
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            content()
        }
    }
}

/**
 * 참여자 개별 아이템 행
 */
@Composable
fun DetailParticipantItem(
    nickname: String,
    travelType: String,
    age: String,
    gender: String,
    isHost: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 아바타 (피그마 48dp, BlueProfile1)
        Box(modifier = Modifier.size(48.dp).background(DduruTheme.colors.BlueProfile1, CircleShape))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = nickname, style = DduruTheme.typography.LabelMedium.copy(fontWeight = FontWeight.Bold))
                if (isHost) {
                    DetailSmallBadge(text = "호스트", isPrimary = true) // 호스트 배지
                }
            }
            // 상세 정보 (Gray600)
            Text(
                text = "$travelType · $age · $gender",
                style = DduruTheme.typography.BodySmall.copy(color = DduruTheme.colors.Gray600)
            )
        }
    }
}
