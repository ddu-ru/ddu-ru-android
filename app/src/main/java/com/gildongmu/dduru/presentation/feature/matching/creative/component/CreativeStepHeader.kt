package com.gildongmu.dduru.presentation.feature.matching.creative.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.creative.model.StepHeaderUiModel

@Composable
fun CreativeStepHeader(
    uiModel: StepHeaderUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.gray0)
    ) {
        Surface(
            color = DduruTheme.colors.secondary100,
            contentColor = DduruTheme.colors.primary,
            shape = RoundedCornerShape(99.dp)
        ) {
            Text(
                text = "STEP ${uiModel.step}",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = DduruTheme.typography.Micro
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = uiModel.title,
            color = DduruTheme.colors.black,
            style = DduruTheme.typography.H1
        )

        uiModel.subtitle?.let { subtitle ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                color = DduruTheme.colors.gray600,
                style = DduruTheme.typography.Body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WhereToLeavePreview() {
    DduruTheme {
        Surface(color = DduruTheme.colors.gray0) {
            CreativeStepHeader(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 52.dp),
                uiModel = StepHeaderUiModel(
                    step = 1,
                    title = "어디로 떠나시나요?",
                    subtitle = "여행지를 선택해 주세요"
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WhenToLeavePreview() {
    DduruTheme {
        Surface(color = DduruTheme.colors.gray0) {
            CreativeStepHeader(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 52.dp),
                uiModel = StepHeaderUiModel(
                    step = 2,
                    title = "언제 떠나시나요?",
                    subtitle = "날짜를 선택해 주세요"
                )
            )
        }
    }
}
