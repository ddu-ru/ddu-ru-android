package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyPostComposeTopBar(
    enabled: Boolean,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
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
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                    shape = CircleShape
                )
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
            text = "게시글 작성",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(99.dp))
                .background(
                    if (enabled) {
                        DduruTheme.colors.primary
                    } else {
                        DduruTheme.colors.gray200
                    }
                )
                .then(if (enabled) Modifier.noRippleClickable(onClick = onSubmitClick) else Modifier)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            Text(
                text = "완료",
                color = if (enabled) {
                    DduruTheme.colors.gray0
                } else {
                    DduruTheme.colors.gray400
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyPostComposeTopBarPreview() {
    DduruTheme {
        JourneyPostComposeTopBar(enabled = true, onBackClick = {}, onSubmitClick = {})
    }
}
