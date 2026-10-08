package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class SettingsMenuItem(
    val title: String,
    val onClick: () -> Unit
)

@Composable
fun SettingsMenuSection(
    label: String,
    items: List<SettingsMenuItem>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray500
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            items.forEach { item ->
                SettingsMenuRow(
                    title = item.title,
                    onClick = item.onClick
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = DduruTheme.colors.gray100
                )
            }
        }
    }
}

@Composable
private fun SettingsMenuRow(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 24.dp)
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray800,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = DduruTheme.colors.gray600,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsMenuSectionPreview() {
    DduruTheme {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            SettingsMenuSection(
                label = "고객 지원",
                items = listOf(
                    SettingsMenuItem(title = "고객센터", onClick = {})
                )
            )

            SettingsMenuSection(
                label = "계정",
                items = listOf(
                    SettingsMenuItem(title = "계정 관리", onClick = {})
                )
            )

            SettingsMenuSection(
                label = "서비스 정보",
                items = listOf(
                    SettingsMenuItem(title = "이용약관", onClick = {}),
                    SettingsMenuItem(title = "회사정보", onClick = {})
                )
            )
        }
    }
}
