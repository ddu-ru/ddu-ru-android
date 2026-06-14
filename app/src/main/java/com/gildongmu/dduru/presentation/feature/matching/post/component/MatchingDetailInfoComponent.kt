package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingDetailInfoComponent(
    @DrawableRes iconRes: Int,
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(20.dp)
            )
            .wrapContentWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = DduruTheme.colors.gray600
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray600
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 9.dp),
            thickness = 0.8.dp,
            color = DduruTheme.colors.gray200
        )

        Text(
            text = content,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailInfoComponentPreview() {
    DduruTheme {
        MatchingDetailInfoComponent(
            iconRes = R.drawable.ic_ai_calendar,
            title = "여행 일정",
            content = "26.03.03(월) ~ 26.04.02(수)",
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
        )
    }
}
