package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ProfileEditHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "프로필 수정",
    onConfirmClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .height(48.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .clip(CircleShape)
                .background(DduruTheme.colors.gray0.copy(alpha = 0.5f))
                .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                .noRippleClickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = "뒤로가기",
                tint = DduruTheme.colors.black,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = title,
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black,
            modifier = Modifier.align(Alignment.Center)
        )

        if (onConfirmClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(DduruTheme.colors.primary)
                    .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.3f), shape = CircleShape)
                    .noRippleClickable(onClick = onConfirmClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "저장",
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "저장 버튼 있음")
@Composable
private fun ProfileEditHeaderWithConfirmPreview() {
    DduruTheme {
        ProfileEditHeader(onBackClick = {}, onConfirmClick = {})
    }
}

@Preview(showBackground = true, name = "저장 버튼 없음")
@Composable
private fun ProfileEditHeaderWithoutConfirmPreview() {
    DduruTheme {
        ProfileEditHeader(onBackClick = {})
    }
}
