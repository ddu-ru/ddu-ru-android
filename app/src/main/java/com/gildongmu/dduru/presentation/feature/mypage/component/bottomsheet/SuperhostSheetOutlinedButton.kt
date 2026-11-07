package com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
internal fun SuperhostSheetOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .border(1.dp, DduruTheme.colors.gray200, RoundedCornerShape(16.dp))
            .noRippleClickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Title1,
            color = DduruTheme.colors.gray700
        )
    }
}
