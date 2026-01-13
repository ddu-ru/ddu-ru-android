package com.gildongmu.dduru_android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun DetailRoundButton(
    iconId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(elevation = 16.dp, spotColor = DduruTheme.colors.BlackAlpha10, shape = RoundedCornerShape(99.dp))
            .shadow(elevation = 8.dp, spotColor = DduruTheme.colors.BlackAlpha08, shape = RoundedCornerShape(99.dp))
            .border(width = 1.dp, color = DduruTheme.colors.WhiteAlpha30, shape = RoundedCornerShape(99.dp))
            .size(48.dp)
            .background(color = DduruTheme.colors.WhiteAlpha50, shape = RoundedCornerShape(99.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
