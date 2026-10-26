package com.gildongmu.dduru.presentation.feature.poll.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar

@Composable
fun PollCreateTopBar(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completeShape = RoundedCornerShape(99.dp)

    Box(modifier = modifier) {
        FloatingBackTopBar(
            title = "투표 생성",
            onBackClick = onBackClick
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp)
                .height(48.dp)
                .widthIn(min = 64.dp)
                .dduruDualShadow(
                    firstAlpha = 0.10f,
                    secondAlpha = 0.08f,
                    shape = completeShape
                )
                .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), completeShape)
                .background(DduruTheme.colors.primary, completeShape)
                .noRippleClickable(onClick = onCompleteClick)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "완료",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun PollCreateTopBarPreview() = DduruTheme {
    PollCreateTopBar(
        onBackClick = {},
        onCompleteClick = {}
    )
}
