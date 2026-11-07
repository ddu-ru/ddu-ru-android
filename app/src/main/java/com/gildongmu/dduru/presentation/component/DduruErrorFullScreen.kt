package com.gildongmu.dduru.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruServiceFailureScreen(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    DduruErrorFullScreenContent(
        iconRes = R.drawable.ic_toast_error,
        title = "서비스 응답 실패",
        description = "서비스 연결이 원활하지 않아요.\n다시 시도해 주세요.",
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            DduruButton(
                text = "다시 시도",
                onClick = onRetryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            if (onBackClick != null) {
                DduruButton(
                    text = "이전 화면으로",
                    onClick = onBackClick,
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

@Composable
fun DduruChatRoomAccessDeniedScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        DduruErrorFullScreenContent(
            iconRes = R.drawable.ic_error_warning,
            title = "접근할 수 없는 채팅방",
            description = "권한이 없거나 채팅방이 종료되었어요.\n이전 화면으로 돌아가 주세요."
        )

        DduruButton(
            text = "뒤로가기",
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}

@Composable
private fun DduruErrorFullScreenContent(
    @DrawableRes iconRes: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
            .padding(top = 104.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = title,
            style = DduruTheme.typography.Title1,
            color = DduruTheme.colors.gray900,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600,
            textAlign = TextAlign.Center
        )

        actions()
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun DduruServiceFailureScreenPreview() {
    DduruTheme {
        DduruServiceFailureScreen(onRetryClick = {}, onBackClick = {})
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun DduruChatRoomAccessDeniedScreenPreview() {
    DduruTheme {
        DduruChatRoomAccessDeniedScreen(onBackClick = {})
    }
}
