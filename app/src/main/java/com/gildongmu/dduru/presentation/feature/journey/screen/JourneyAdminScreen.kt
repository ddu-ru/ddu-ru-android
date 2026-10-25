package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminKickBottomSheet
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminKickConfirmDialog
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminMemberList
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminMemberManagement
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminRoleBottomSheet
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminTopBar
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminUiState
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyAdminViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun JourneyAdminRoute(
    journeyId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyAdminViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var roleMember by remember { mutableStateOf<JourneyAdminMember?>(null) }
    var selectedRoles by remember { mutableStateOf<ImmutableList<String>>(persistentListOf()) }
    var isKickBottomSheetVisible by remember { mutableStateOf(false) }
    var kickTargetMember by remember { mutableStateOf<JourneyAdminMember?>(null) }

    LaunchedEffect(journeyId) { viewModel.loadMembers(journeyId) }
    LaunchedEffect(viewModel) {
        viewModel.roleUpdateSucceeded.collect { memberId ->
            if (roleMember?.id == memberId) roleMember = null
        }
    }

    when (val state = uiState) {
        UiState.Empty -> Unit
        UiState.Loading -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "여정 멤버를 불러오지 못했어요.")
        is UiState.Success -> JourneyAdminScreen(
            uiState = state.data,
            onBackClick = onBackClick,
            onMemberClick = { memberId ->
                state.data.members.find { it.id == memberId }?.let { member ->
                    roleMember = member
                    selectedRoles = member.roles
                }
            },
            onKickMemberClick = { isKickBottomSheetVisible = true },
            modifier = modifier
        )
    }

    roleMember?.let { member ->
        JourneyAdminRoleBottomSheet(
            member = member,
            selectedRoles = selectedRoles,
            onRoleClick = { role ->
                selectedRoles = if (role in selectedRoles) {
                    selectedRoles.filterNot { it == role }.toImmutableList()
                } else {
                    (selectedRoles + role).toImmutableList()
                }
            },
            onCustomRoleAdd = { role ->
                if (role !in selectedRoles) selectedRoles = (selectedRoles + role).toImmutableList()
            },
            onDismiss = {
                roleMember = null
            },
            onConfirm = {
                viewModel.updateMemberRoles(member.id, selectedRoles)
            }
        )
    }

    if (isKickBottomSheetVisible) {
        JourneyAdminKickBottomSheet(
            members = (uiState as? UiState.Success)?.data?.members ?: persistentListOf(),
            onKickClick = { member ->
                kickTargetMember = member
            },
            onDismiss = {
                isKickBottomSheetVisible = false
                kickTargetMember = null
            }
        )
    }

    kickTargetMember?.let { member ->
        JourneyAdminKickConfirmDialog(
            onDismiss = {
                kickTargetMember = null
            },
            onConfirm = {
                viewModel.kickMember(member.id)
                kickTargetMember = null
                isKickBottomSheetVisible = false
            }
        )
    }
}

@Composable
fun JourneyAdminScreen(
    uiState: JourneyAdminUiState,
    onBackClick: () -> Unit,
    onMemberClick: (Long) -> Unit,
    onKickMemberClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        JourneyAdminTopBar(
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(40.dp))

        androidx.compose.material3.Text(
            text = "멤버별 역할",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(20.dp))

        JourneyAdminMemberList(
            members = uiState.members,
            onMemberClick = onMemberClick
        )

        Spacer(modifier = Modifier.height(44.dp))

        JourneyAdminMemberManagement(
            onKickMemberClick = onKickMemberClick
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun JourneyAdminScreenPreview() {
    val blankProfileImage =
        "android.resource://com.gildongmu.dduru/drawable/img_blank_profile"
    var uiState by remember {
        mutableStateOf(
            JourneyAdminUiState(
                members = persistentListOf(
                    JourneyAdminMember(
                        id = 1L,
                        username = "username",
                        profileImageUrl = blankProfileImage,
                        roles = persistentListOf(
                            "호스트",
                            "총무"
                        )
                    ),
                    JourneyAdminMember(
                        id = 2L,
                        username = "username",
                        profileImageUrl = blankProfileImage,
                        roles = persistentListOf("총무")
                    ),
                    JourneyAdminMember(
                        id = 3L,
                        username = "username",
                        profileImageUrl = blankProfileImage,
                        roles = persistentListOf("총무")
                    ),
                    JourneyAdminMember(
                        id = 4L,
                        username = "username",
                        profileImageUrl = blankProfileImage,
                        roles = persistentListOf("총무")
                    ),
                    JourneyAdminMember(
                        id = 5L,
                        username = "username",
                        profileImageUrl = blankProfileImage,
                        roles = persistentListOf("총무")
                    )
                )
            )
        )
    }
    var roleMember by remember { mutableStateOf<JourneyAdminMember?>(null) }
    var selectedRoles by remember { mutableStateOf<ImmutableList<String>>(persistentListOf()) }
    var isKickBottomSheetVisible by remember { mutableStateOf(false) }
    var kickTargetMember by remember { mutableStateOf<JourneyAdminMember?>(null) }

    DduruTheme {
        JourneyAdminScreen(
            uiState = uiState,
            onBackClick = {},
            onMemberClick = { memberId ->
                uiState.members.find { it.id == memberId }?.let { member ->
                    roleMember = member
                    selectedRoles = member.roles
                }
            },
            onKickMemberClick = {
                isKickBottomSheetVisible = true
            }
        )

        roleMember?.let { member ->
            JourneyAdminRoleBottomSheet(
                member = member,
                selectedRoles = selectedRoles,
                onRoleClick = { role ->
                    selectedRoles = if (role in selectedRoles) {
                        selectedRoles.filterNot { it == role }.toImmutableList()
                    } else {
                        (selectedRoles + role).toImmutableList()
                    }
                },
                onCustomRoleAdd = {
                },
                onDismiss = {
                    roleMember = null
                },
                onConfirm = {
                    uiState = uiState.copy(
                        members = uiState.members.map { previewMember ->
                            if (previewMember.id == member.id) {
                                previewMember.copy(roles = selectedRoles)
                            } else {
                                previewMember
                            }
                        }.toImmutableList()
                    )
                    roleMember = null
                }
            )
        }

        if (isKickBottomSheetVisible) {
            JourneyAdminKickBottomSheet(
                members = uiState.members,
                onKickClick = { member ->
                    kickTargetMember = member
                },
                onDismiss = {
                    isKickBottomSheetVisible = false
                    kickTargetMember = null
                }
            )
        }

        kickTargetMember?.let { member ->
            JourneyAdminKickConfirmDialog(
                onDismiss = {
                    kickTargetMember = null
                },
                onConfirm = {
                    uiState = uiState.copy(
                        members = uiState.members.filterNot { it.id == member.id }.toImmutableList()
                    )
                    kickTargetMember = null
                    isKickBottomSheetVisible = false
                }
            )
        }
    }
}
