package com.gildongmu.dduru.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruIconButton(
    onClick: () -> Unit,
    @DrawableRes iconRes: Int = R.drawable.ic_close,
    backgroundColor: Color = DduruTheme.colors.gray0,
    iconTint: Color = DduruTheme.colors.black,
    buttonSize: Dp = 48.dp,
    iconSize: Dp = 12.dp,
    shape: Shape = CircleShape,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(buttonSize)
            .dduruDualShadow(
                firstAlpha = 0.08f,
                secondAlpha = 0.10f,
                shape = shape
            )
            .clip(shape)
            .background(backgroundColor)
            .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}
