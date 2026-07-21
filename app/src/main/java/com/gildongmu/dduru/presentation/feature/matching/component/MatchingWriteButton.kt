package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun MatchingWriteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .dduruDualShadow(
                firstAlpha = 0.12f,
                secondAlpha = 0.08f,
                shape = RoundedCornerShape(99.dp),
                firstElevation = 8.dp,
                secondElevation = 4.dp
            )
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray50)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray9E.copy(alpha = 0.3f),
                shape = RoundedCornerShape(99.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 17.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_write_plus),
            contentDescription = null,
            tint = DduruTheme.colors.gray700,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "글쓰기",
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray700
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingWriteButtonPreview() {
    DduruTheme {
        MatchingWriteButton(onClick = {})
    }
}
