package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatRoomMenuCard(
    imageUrl: String?,
    title: String,
    participantCount: Int,
    onNavigateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(1.dp, DduruTheme.colors.gray200, shape)
            .background(DduruTheme.colors.gray0, shape)
            .padding(16.dp)
    ) {
        ChatRoomMenuCardContent(
            imageUrl = imageUrl,
            title = title,
            participantCount = participantCount
        )

        DduruButton(
            text = "나의 여정으로 가기",
            onClick = onNavigateClick,
            enabledColors = DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.gray100,
                textColor = DduruTheme.colors.gray700,
                borderColor = DduruTheme.colors.gray300
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomMenuCardPreview() {
    DduruTheme {
        ChatRoomMenuCard(
            imageUrl = null,
            title = "제주 애월 2박 3일",
            participantCount = 3,
            onNavigateClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
