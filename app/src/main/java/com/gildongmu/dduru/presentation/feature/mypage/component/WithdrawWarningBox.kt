package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun WithdrawWarningBox(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(14.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DduruTheme.colors.rejectConfirmBg, shape = shape)
            .border(width = 1.dp, color = DduruTheme.colors.rejectRedBg, shape = shape)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_warning),
            contentDescription = null,
            tint = DduruTheme.colors.rejectRed,
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = title,
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.rejectRed
        )

        Text(
            text = description,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray868
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawWarningBoxPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            WithdrawWarningBox(
                title = "탈퇴 전 꼭 확인해주세요",
                description = "탈퇴 시 계정 및 모든 데이터가 삭제되며, 복구할 수 없습니다.\n탈퇴 전 아래 내용을 꼭 확인해주세요.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
