package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailSmallBadge(
    text: String,
    isPrimary: Boolean = true
) {
    // 테마 토큰 기반 색상 결정
    val bgColor = if (isPrimary) {
        DduruTheme.colors.Secondary
    } else {
        DduruTheme.colors.Gray200
    }
    val textColor = if (isPrimary) {
        DduruTheme.colors.Primary
    } else {
        DduruTheme.colors.Gray700
    }

    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.LabelSmall.copy(
                fontSize = 10.sp,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
