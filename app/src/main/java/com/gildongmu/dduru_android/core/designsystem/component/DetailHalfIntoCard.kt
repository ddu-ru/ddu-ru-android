package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailHalfInfoCard(
    iconId: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    badgeText: String? = null // D-6 배지 등
) {
    Column(
        modifier = modifier
            .height(81.dp) // 피그마 Height 반영
            .background(DduruTheme.colors.Gray100, RoundedCornerShape(16.dp))
            .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 아이콘 + 라벨 ( Gray700, 12sp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(painter = painterResource(id = iconId), contentDescription = null, modifier = Modifier.size(16.dp))
            Text(text = label, style = DduruTheme.typography.HomeFilter.copy(color = DduruTheme.colors.Gray700))
        }
        // 수치 + 배지 ( Gray800, 16sp Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = value,
                style = DduruTheme.typography.HomeBanner.copy(
                    fontSize = 16.sp,
                    color = DduruTheme.colors.Gray800
                )
            )
            badgeText?.let {
                DetailSmallBadge(text = it, isPrimary = true) // Blue 배지
            }
        }
    }
}
