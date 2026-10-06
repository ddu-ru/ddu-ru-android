package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TravelStyleTagChip(
    text: String,
    isActive: Boolean,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .alpha(if (isActive) 1f else 0.3f)
            .background(color = DduruTheme.colors.gray200, shape = CircleShape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray700
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_x_gray),
            contentDescription = if (isActive) "태그 끄기" else "태그 켜기",
            tint = DduruTheme.colors.gray700,
            modifier = Modifier
                .size(12.dp)
                .noRippleClickable(onClick = onToggleClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelStyleTagChipPreview() {
    DduruTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TravelStyleTagChip(text = "🍜  맛집러", isActive = true, onToggleClick = {})
            TravelStyleTagChip(text = "🌃  야경좋아함", isActive = false, onToggleClick = {})
        }
    }
}
