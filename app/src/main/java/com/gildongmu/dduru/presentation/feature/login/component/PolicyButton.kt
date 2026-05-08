package com.gildongmu.dduru.presentation.feature.login.component

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PolicyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Text(
        modifier = modifier
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        text = "시작하기를 눌러 회원가입 완료 시\n" +
            "본 서비스의 약관에 동의한 것으로 간주합니다.",
        textAlign = TextAlign.Center,
        style = DduruTheme.typography.Caption2,
        color = DduruTheme.colors.gray600
    )
}

@Preview(showBackground = true)
@Composable
fun PolicyButtonPreview() {
    DduruTheme {
        PolicyButton(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
