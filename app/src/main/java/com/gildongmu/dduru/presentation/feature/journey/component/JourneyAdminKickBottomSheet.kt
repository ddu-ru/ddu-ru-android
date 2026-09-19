package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember

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

                Text(
                    text = "×",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .noRippleClickable(onClick = onDismiss)
                        .padding(8.dp),
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.gray500
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            members.forEachIndexed { index, member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JourneyAdminMemberInfo(
                        member = member,
                        modifier = Modifier.weight(1f)
                    )

                    DduruButton(
                        text = "강퇴",
                        onClick = { onKickClick(member) },
                        enabledColors = DduruButtonDefaults.whiteEnabledColors,
                        disabledColors = DduruButtonDefaults.whiteDisabledColors
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
