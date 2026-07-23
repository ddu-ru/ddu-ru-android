package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.05f,
                secondElevation = 8.dp
            )
            .size(48.dp)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                shape = RoundedCornerShape(size = 99.dp)
            )
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                shape = RoundedCornerShape(size = 99.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "뒤로가기",
            tint = DduruTheme.colors.gray900,
            modifier = Modifier.size(24.dp)
        )
    }
}
