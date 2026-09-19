package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyAdminRoleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(
                if (selected) {
                    DduruTheme.colors.primary
                } else {
                    DduruTheme.colors.gray50
                }
            )
            .noRippleClickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Body2,
            color = if (selected) {
                DduruTheme.colors.gray0
            } else {
                DduruTheme.colors.gray600
            }
        )

        if (selected) {
            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onClick,
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_x_pass),
                    contentDescription = null,
                    modifier = Modifier.size(5.dp),
                    tint = DduruTheme.colors.gray0
                )
            }
        }
    }
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
