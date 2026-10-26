package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun AgreementCheckRow(
    checked: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .then(
                    if (checked) {
                        Modifier.background(color = DduruTheme.colors.primary, shape = CircleShape)
                    } else {
                        Modifier
                            .background(color = DduruTheme.colors.gray0, shape = CircleShape)
                            .border(width = 1.dp, color = DduruTheme.colors.gray300, shape = CircleShape)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(10.dp)
                )
            }
        }

        Text(
            text = text,
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray868
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementCheckRowPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AgreementCheckRow(
                    checked = false,
                    text = "위 내용을 모두 확인했으며, 회원탈퇴에 동의합니다.",
                    onCheckedChange = {}
                )
                AgreementCheckRow(
                    checked = true,
                    text = "위 내용을 모두 확인했으며, 회원탈퇴에 동의합니다.",
                    onCheckedChange = {}
                )
            }
        }
    }
}
