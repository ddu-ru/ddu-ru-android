package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun StatusTagXs(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DduruTheme.colors.gray100,
    textColor: Color = DduruTheme.colors.gray700,
    textStyle: TextStyle = DduruTheme.typography.Micro2,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    contentSpacing: Dp = 4.dp,
    leadingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 99.dp))
            .background(color = backgroundColor)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(),
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingContent?.let {
                it()
                Spacer(modifier = Modifier.width(contentSpacing))
            }

            Text(
                text = text,
                color = textColor,
                style = textStyle
            )
        }
    }
}

@Preview(name = "StatusTagXs - 수정")
@Composable
private fun StatusTagXsEditPreview() {
    DduruTheme {
        StatusTagXs(text = "수정", onClick = {})
    }
}

@Preview(name = "StatusTagXs - 텍스트만")
@Composable
private fun StatusTagXsDefaultPreview() {
    DduruTheme {
        StatusTagXs(text = "파리")
    }
}
