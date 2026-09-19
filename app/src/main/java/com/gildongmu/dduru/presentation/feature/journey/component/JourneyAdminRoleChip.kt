package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyAdminRoleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (selected) "$text  ×" else text,
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(
                if (selected) {
                    DduruTheme.colors.primary
                } else {
                    DduruTheme.colors.gray100
                }
            )
            .noRippleClickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        style = DduruTheme.typography.Body2,
        color = if (selected) {
            DduruTheme.colors.gray0
        } else {
            DduruTheme.colors.gray500
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun JourneyAdminRoleChipPreview() {
    DduruTheme {
        JourneyAdminRoleChip(
            text = "총무",
            selected = true,
            onClick = {}
        )
    }
}
