package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyEmptyCard(
    onExploreClick: () -> Unit,
    onCreateJourneyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 40.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_home_empty_penguin),
            contentDescription = null,
            modifier = Modifier.width(92.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "아직 참여 중인 여정이 없어요",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.gray928,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "여행에 참여하면 일정과 할 일, 새로운 소식을\n이곳에서 한눈에 확인할 수 있어요",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        DduruButton(
            text = "다른 여행 둘러보기",
            onClick = onExploreClick,
            enabledColors = DduruButtonDefaults.filledEnabledColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(12.dp))

        DduruButton(
            text = "＋ 여행 만들기",
            onClick = onCreateJourneyClick,
            enabledColors = DduruButtonDefaults.whiteEnabledColors,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun JourneyEmptyCardPreview() {
    DduruTheme {
        JourneyEmptyCard(
            onExploreClick = { },
            onCreateJourneyClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}
