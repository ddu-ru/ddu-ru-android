package com.gildongmu.dduru.presentation.feature.tripgroup.creative.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun TransparentButton(
    text: String,
    hazeState: HazeState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
) {
    val containerShape = RoundedCornerShape(99.dp)

    Box(
        modifier = Modifier
            .hazeChild(
                state = hazeState,
                shape = containerShape,
                style = HazeStyle(
                    tint = DduruTheme.colors.gray50.copy(alpha = 0.14f),
                    blurRadius = 8.dp,
                    noiseFactor = 0f,
                ),
            )
            .background(color = Color.Transparent, shape = containerShape)
            .border(
                width = 0.8.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.2f),
                shape = containerShape,
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ){

        Text(
            text = text,
            modifier = Modifier.padding(contentPadding),
            style = DduruTheme.typography.Micro,
            color = DduruTheme.colors.gray0,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransparentButtonPreview() {
    val hazeState = remember { HazeState() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0xFF5A6F95))
                .haze(hazeState),
        )

        TransparentButton(
            text = "사진 변경",
            hazeState = hazeState,
            onClick = {},
        )
    }
}
