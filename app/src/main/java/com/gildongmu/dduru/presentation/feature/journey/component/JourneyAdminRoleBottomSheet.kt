package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.gildongmu.dduru.presentation.feature.journey.state.DEFAULT_JOURNEY_ROLES
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyAdminRoleBottomSheet(
    member: JourneyAdminMember,
    selectedRoles: List<String>,
    onRoleClick: (String) -> Unit,
    onCustomRoleClick: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "역할 설정",
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.black
                )

                Text(
                    text = "×",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .noRippleClickable(onClick = onDismiss)
                        .padding(12.dp),
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.gray500
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            JourneyAdminMemberInfo(
                member = member,
                showBackground = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "선택된 역할",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray800
            )

            Spacer(modifier = Modifier.height(16.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DEFAULT_JOURNEY_ROLES.forEach { role ->
                    JourneyAdminRoleChip(
                        text = role,
                        selected = role in selectedRoles,
                        onClick = {
                            onRoleClick(role)
                        }
                    )
                }

                selectedRoles
                    .filter { it !in DEFAULT_JOURNEY_ROLES }
                    .forEach { role ->
                        JourneyAdminRoleChip(
                            text = role,
                            selected = true,
                            onClick = {
                                onRoleClick(role)
                            }
                        )
                    }

                JourneyAdminRoleChip(
                    text = "＋ 직접 입력",
                    selected = false,
                    onClick = onCustomRoleClick
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "취소",
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    enabledColors = DduruButtonDefaults.whiteEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors
                )

                DduruButton(
                    text = "완료",
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
