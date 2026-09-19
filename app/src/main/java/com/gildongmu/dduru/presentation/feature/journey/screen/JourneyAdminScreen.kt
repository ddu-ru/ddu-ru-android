package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminMemberList
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminMemberManagement
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminTopBar
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyMemberRole
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminUiState
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyAdminViewModel

@Composable
fun JourneyAdminRoute(
    onBackClick: () -> Unit,
    onMemberClick: (Long) -> Unit,
    onKickMemberClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JourneyAdminViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    JourneyAdminScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onMemberClick = onMemberClick,
        onKickMemberClick = onKickMemberClick,
        modifier = modifier,
    )
}

@Composable
fun JourneyAdminScreen(
    uiState: JourneyAdminUiState,
    onBackClick: () -> Unit,
    onMemberClick: (Long) -> Unit,
    onKickMemberClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        JourneyAdminTopBar(
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(40.dp))

        androidx.compose.material3.Text(
            text = "멤버별 역할",
            style = com.gildongmu.dduru.core.designsystem.theme.DduruTheme.typography.Body1,
            color = com.gildongmu.dduru.core.designsystem.theme.DduruTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(20.dp))

        JourneyAdminMemberList(
            members = uiState.members,
            onMemberClick = onMemberClick,
        )

        Spacer(modifier = Modifier.height(44.dp))

        JourneyAdminMemberManagement(
            onKickMemberClick = onKickMemberClick,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun JourneyAdminScreenPreview() {
    DduruTheme {
        JourneyAdminScreen(
            uiState = JourneyAdminUiState(
                members = listOf(
                    JourneyAdminMember(
                        id = 1L,
                        username = "username",
                        roles = listOf(
                            JourneyMemberRole.HOST,
                            JourneyMemberRole.GENERAL_AFFAIRS,
                        ),
                    ),
                    JourneyAdminMember(
                        id = 2L,
                        username = "username",
                        roles = listOf(
                            JourneyMemberRole.GENERAL_AFFAIRS,
                        ),
                    ),
                    JourneyAdminMember(
                        id = 3L,
                        username = "username",
                        roles = listOf(
                            JourneyMemberRole.GENERAL_AFFAIRS,
                        ),
                    ),
                    JourneyAdminMember(
                        id = 4L,
                        username = "username",
                        roles = listOf(
                            JourneyMemberRole.GENERAL_AFFAIRS,
                        ),
                    ),
                    JourneyAdminMember(
                        id = 5L,
                        username = "username",
                        roles = listOf(
                            JourneyMemberRole.GENERAL_AFFAIRS,
                        ),
                    ),
                ),
            ),
            onBackClick = {},
            onMemberClick = {},
            onKickMemberClick = {},
        )
    }
}
