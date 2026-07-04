package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatActionBar(
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = shape,
                spotColor = DduruTheme.colors.black.copy(alpha = 0.10f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.10f)
            )
            .shadow(
                elevation = 8.dp,
                shape = shape,
                spotColor = DduruTheme.colors.black.copy(alpha = 0.08f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.08f)
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                shape = shape
            )
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.50f),
                shape = shape
            )
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_matching_searching),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onSearchClick),
            tint = DduruTheme.colors.black
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onMenuClick),
            tint = DduruTheme.colors.black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatActionBarPreview() {
    DduruTheme {
        ChatActionBar(
            onSearchClick = {},
            onMenuClick = {}
        )
    }
}
