package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar

@Composable
fun ProfileEditHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "프로필 수정",
    onConfirmClick: (() -> Unit)? = null,
    isConfirmEnabled: Boolean = true
) {
    FloatingBackTopBar(
        title = title,
        onBackClick = onBackClick,
        modifier = modifier,
        trailingContent = {
            if (onConfirmClick != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(48.dp)
                        .dduruDualShadow(
                            firstAlpha = 0.10f,
                            secondAlpha = 0.08f,
                            shape = CircleShape
                        )
                        .background(
                            color = if (isConfirmEnabled) DduruTheme.colors.primary else DduruTheme.colors.gray200,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = if (isConfirmEnabled) {
                                DduruTheme.colors.gray0.copy(alpha = 0.3f)
                            } else {
                                DduruTheme.colors.gray300
                            },
                            shape = CircleShape
                        )
                        .then(if (isConfirmEnabled) Modifier.noRippleClickable(onClick = onConfirmClick) else Modifier),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = "저장",
                        tint = if (isConfirmEnabled) DduruTheme.colors.gray0 else DduruTheme.colors.gray400,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, name = "저장 버튼 있음")
@Composable
private fun ProfileEditHeaderWithConfirmPreview() {
    DduruTheme {
        ProfileEditHeader(onBackClick = {}, onConfirmClick = {})
    }
}

@Preview(showBackground = true, name = "저장 버튼 비활성")
@Composable
private fun ProfileEditHeaderConfirmDisabledPreview() {
    DduruTheme {
        ProfileEditHeader(onBackClick = {}, onConfirmClick = {}, isConfirmEnabled = false)
    }
}

@Preview(showBackground = true, name = "저장 버튼 없음")
@Composable
private fun ProfileEditHeaderWithoutConfirmPreview() {
    DduruTheme {
        ProfileEditHeader(onBackClick = {})
    }
}
