package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember
import com.gildongmu.dduru.presentation.feature.journey.state.journeyAdminPreviewMembers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyAdminKickBottomSheet(
    members: List<JourneyAdminMember>,
    onKickClick: (JourneyAdminMember) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        containerColor = DduruTheme.colors.gray0
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "멤버 강퇴",
                    modifier = Modifier.align(Alignment.Center),
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.black
                )

                Icon(
                    painter = painterResource(R.drawable.ic_x_pass),
                    contentDescription = "닫기",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp)
                        .noRippleClickable(onClick = onDismiss),
                    tint = DduruTheme.colors.gray500
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            members.forEachIndexed { index, member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JourneyAdminMemberInfo(
                        member = member,
                        modifier = Modifier.weight(1f)
                    )

                    DduruButton(
                        text = "강퇴",
                        onClick = { onKickClick(member) },
                        enabledColors = DduruButtonEnabledColors(
                            backgroundColor = DduruTheme.colors.gray0,
                            textColor = DduruTheme.colors.journeyKick,
                            borderColor = DduruTheme.colors.gray200
                        ),
                        disabledColors = DduruButtonDefaults.whiteDisabledColors,
                        contentPadding = PaddingValues(horizontal = 36.dp, vertical = 13.dp),
                        shape = RoundedCornerShape(99.dp)
                    )
                }

                if (index != members.lastIndex) {
                    HorizontalDivider(
                        color = DduruTheme.colors.gray200
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            DduruButton(
                text = "취소",
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                enabledColors = DduruButtonDefaults.whiteEnabledColors,
                disabledColors = DduruButtonDefaults.whiteDisabledColors
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyAdminKickBottomSheetPreview() {
    DduruTheme {
        JourneyAdminKickBottomSheet(
            members = journeyAdminPreviewMembers,
            onKickClick = {},
            onDismiss = {}
        )
    }
}
