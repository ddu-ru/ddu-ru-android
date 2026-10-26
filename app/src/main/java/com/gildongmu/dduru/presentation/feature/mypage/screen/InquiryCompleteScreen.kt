package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruIconButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun InquiryCompleteScreen(
    onCloseClick: () -> Unit,
    onViewInquiriesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.30f to DduruTheme.colors.gray0.copy(alpha = 0.8f),
                            0.767f to DduruTheme.colors.gray0.copy(alpha = 0.5f),
                            1f to DduruTheme.colors.gray0.copy(alpha = 0f)
                        )
                    )
                )
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            DduruIconButton(
                onClick = onCloseClick,
                iconRes = R.drawable.ic_close,
                iconSize = 24.dp,
                backgroundColor = DduruTheme.colors.gray0.copy(alpha = 0.92f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(color = DduruTheme.colors.secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = DduruTheme.colors.primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "문의가 접수되었어요",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray900,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "답변이 등록되면 알림으로 안내드려요.\n문의내역에서 확인할 수 있어요.",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray600,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "문의내역 확인",
                    onClick = onViewInquiriesClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )

                DduruButton(
                    text = "닫기",
                    onClick = onCloseClick,
                    enabledColors = DduruButtonEnabledColors(
                        backgroundColor = DduruTheme.colors.gray0,
                        textColor = DduruTheme.colors.gray500,
                        borderColor = DduruTheme.colors.gray200
                    ),
                    textStyle = DduruTheme.typography.Title2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 812, name = "문의 접수 완료")
@Composable
private fun InquiryCompleteScreenPreview() {
    DduruTheme {
        InquiryCompleteScreen(
            onCloseClick = {},
            onViewInquiriesClick = {}
        )
    }
}
