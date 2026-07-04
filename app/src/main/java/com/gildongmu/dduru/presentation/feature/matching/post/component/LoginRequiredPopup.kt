package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun LoginRequiredPopup(
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onBackClick
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            color = DduruTheme.colors.gray0
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_home_empty_penguin),
                    contentDescription = null,
                    modifier = Modifier.width(69.dp)
                )

                Spacer(modifier = Modifier.size(26.dp))

                Text(
                    text = "이 여행, 더 자세히 보고 싶다면?",
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.gray800,
                    modifier = Modifier.padding(horizontal = 7.dp)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    text = "로그인하면 일정이랑 참여 멤버까지 모두 볼 수 있어요.\n여행 일정, 호스트, 참여 멤버까지 한눈에 확인하세요!",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600
                )

                Spacer(modifier = Modifier.size(26.dp))

                LoginBenefitBox()

                Spacer(modifier = Modifier.size(26.dp))

                DduruButton(
                    text = "로그인",
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = "돌아가기",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.noRippleClickable(onClick = onBackClick)
                )
            }
        }
    }
}

@Composable
private fun LoginBenefitBox(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DduruTheme.colors.gray100,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "로그인하면",
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray900
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoginBenefitItem(text = "일정 & 모집 조건 확인 가능")

        Spacer(modifier = Modifier.height(8.dp))

        LoginBenefitItem(text = "호스트 프로필과 참여 멤버 확인")

        Spacer(modifier = Modifier.height(8.dp))

        LoginBenefitItem(text = "찜하기, 문의하기, 동행 신청 가능")
    }
}

@Composable
private fun LoginBenefitItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null,
            tint = DduruTheme.colors.gray700,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray700
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginRequiredPopupPreview() {
    DduruTheme {
        LoginRequiredPopup(
            onLoginClick = {},
            onBackClick = {}
        )
    }
}
