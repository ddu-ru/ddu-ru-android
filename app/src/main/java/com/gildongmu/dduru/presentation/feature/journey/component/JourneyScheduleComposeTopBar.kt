package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyScheduleComposeTopBar(
    enabled: Boolean,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .dduruDualShadow(
                    firstAlpha = 0.08f,
                    secondAlpha = 0.10f,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(DduruTheme.colors.gray0)
                .noRippleClickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                tint = DduruTheme.colors.black,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "일정 생성",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black
        )

        DduruButton(
            text = "완료",
            onClick = onSubmitClick,
            enabled = enabled,
            enabledColors = DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.primary,
                textColor = DduruTheme.colors.gray0,
                borderColor = Color.Transparent
            ),
            disabledColors = DduruButtonDisabledColors(
                backgroundColor = DduruTheme.colors.gray200,
                textColor = DduruTheme.colors.gray400,
                borderColor = Color.Transparent
            ),
            textStyle = DduruTheme.typography.Body2,
            shape = RoundedCornerShape(99.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleComposeTopBarPreview() = DduruTheme {
    JourneyScheduleComposeTopBar(
        enabled = true,
        onBackClick = {},
        onSubmitClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
