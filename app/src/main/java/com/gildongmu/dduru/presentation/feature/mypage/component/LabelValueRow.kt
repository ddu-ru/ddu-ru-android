package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun LabelValueRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp),
            color = DduruTheme.colors.gray500,
            modifier = Modifier.width(48.dp)
        )
        Text(
            text = value,
            style = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp),
            color = DduruTheme.colors.gray700
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelValueRowPreview() {
    DduruTheme {
        LabelValueRow(label = "생년월일", value = "1998.03.14")
    }
}
