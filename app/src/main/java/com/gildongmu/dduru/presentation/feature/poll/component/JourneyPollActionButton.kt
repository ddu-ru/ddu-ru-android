package com.gildongmu.dduru.presentation.feature.poll.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyPollActionButton(
    hasVoted: Boolean,
    enabled: Boolean,
    onVoteClick: () -> Unit,
    onRevoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val enabledColors = if (hasVoted) {
        DduruButtonDefaults.whiteEnabledColors
    } else {
        DduruButtonDefaults.filledEnabledColors
    }

    DduruButton(
        text = if (hasVoted) "투표 다시 하기" else "투표 완료 하기",
        onClick = if (hasVoted) onRevoteClick else onVoteClick,
        enabled = hasVoted || enabled,
        enabledColors = enabledColors,
        disabledColors = DduruButtonDefaults.whiteDisabledColors,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyPollActionButtonPreview() = DduruTheme {
    JourneyPollActionButton(
        hasVoted = false,
        enabled = true,
        onVoteClick = {},
        onRevoteClick = {}
    )
}
