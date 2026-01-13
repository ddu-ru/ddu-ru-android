package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailDateRangeBox(
    startDate: String,
    endDate: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 시작 날짜 박스
        DateBox(date = startDate, modifier = Modifier.weight(1f))

        // 중간 대시 (-)
        Text(
            text = "-",
            modifier = Modifier.padding(horizontal = 8.dp),
            style = DduruTheme.typography.TitleSmall.copy(color = DduruTheme.colors.Black)
        )

        // 종료 날짜 박스
        DateBox(date = endDate, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DateBox(date: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(DduruTheme.colors.Gray100, RoundedCornerShape(16.dp)) // F1F3F4
            .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp)) // DADCE0
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date,
            style = DduruTheme.typography.BodyMedium.copy(
                fontSize = 14.sp,
                color = DduruTheme.colors.Black
            )
        )
    }
}
