package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit // 👈 1. String을 @Composable 람다로 변경
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.Gray100, RoundedCornerShape(16.dp))
            .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 2. 제목이 있을 때만 텍스트를 보여줍니다.
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = DduruTheme.typography.HomeFilter.copy(
                    fontWeight = FontWeight.Bold,
                    color = DduruTheme.colors.Gray800
                )
            )
        }

        // 3. 이 자리에 주입된 UI(Text, Row 등)가 들어옵니다.
        content()
    }
}
