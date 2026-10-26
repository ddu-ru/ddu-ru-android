package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TravelStyleTestSection(
    onStartTestClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "나의 여행 스타일"
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray100)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = label,
                        style = DduruTheme.typography.Caption2,
                        color = DduruTheme.colors.gray400
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "나만의 여행 성향을\n찾아보세요!",
                            style = DduruTheme.typography.Title2,
                            color = DduruTheme.colors.gray868
                        )
                        Text(
                            text = "간단한 설문으로 당신의 여행 성향과\n추천 여행지를 확인할 수 있어요",
                            style = DduruTheme.typography.Caption2,
                            color = DduruTheme.colors.gray500
                        )
                    }
                }

                DduruButton(
                    text = "여행 성향 테스트 시작하기",
                    onClick = onStartTestClick,
                    textStyle = DduruTheme.typography.Title2,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Image(
                painter = painterResource(id = R.drawable.img_travel_style_character),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(97.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelStyleTestSectionPreview() {
    DduruTheme {
        TravelStyleTestSection(onStartTestClick = {})
    }
}
