package com.gildongmu.dduru.presentation.feature.poll.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.poll.component.PollCreateInputSection
import com.gildongmu.dduru.presentation.feature.poll.component.PollCreateTopBar
import com.gildongmu.dduru.presentation.feature.poll.component.PollDatePicker
import com.gildongmu.dduru.presentation.feature.poll.component.PollOptionCheckboxGroup
import com.gildongmu.dduru.presentation.feature.poll.state.PollCreateUiState

@Composable
fun PollCreateScreen(
    uiState: PollCreateUiState,
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onSubjectChange: (Int, String) -> Unit,
    onSubjectRemove: (Int) -> Unit,
    onAddSubject: () -> Unit,
    onMultipleChoiceChange: (Boolean) -> Unit,
    onAnonymousChange: (Boolean) -> Unit,
    onAllowAddOptionsChange: (Boolean) -> Unit,
    onEndDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        PollCreateTopBar(
            onBackClick = onBackClick,
            onCompleteClick = onCompleteClick
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 32.dp)
        ) {
            PollCreateInputSection(
                title = uiState.title,
                subjects = uiState.subjects,
                onTitleChange = onTitleChange,
                onSubjectChange = onSubjectChange,
                onSubjectRemove = onSubjectRemove,
                onAddSubject = onAddSubject
            )

            PollOptionCheckboxGroup(
                multipleChoice = uiState.multipleChoice,
                anonymous = uiState.anonymous,
                allowAddOptions = uiState.allowAddOptions,
                onMultipleChoiceChange = onMultipleChoiceChange,
                onAnonymousChange = onAnonymousChange,
                onAllowAddOptionsChange = onAllowAddOptionsChange
            )

            PollDatePicker(
                dateText = uiState.endDate,
                timeText = uiState.endTime,
                onClick = onEndDateClick
            )
        }
    }
}

@Composable
private fun PollCreateScreenPreviewContent(initialState: PollCreateUiState) = DduruTheme {
    var uiState by remember { mutableStateOf(initialState) }

    PollCreateScreen(
        uiState = uiState,
        onBackClick = {},
        onCompleteClick = {},
        onTitleChange = { uiState = uiState.copy(title = it) },
        onSubjectChange = { index, value ->
            uiState = uiState.copy(
                subjects = uiState.subjects.toMutableList().apply { this[index] = value }
            )
        },
        onSubjectRemove = { index ->
            uiState = uiState.copy(
                subjects = uiState.subjects.toMutableList().apply { removeAt(index) }
            )
        },
        onAddSubject = { uiState = uiState.copy(subjects = uiState.subjects + "") },
        onMultipleChoiceChange = { uiState = uiState.copy(multipleChoice = it) },
        onAnonymousChange = { uiState = uiState.copy(anonymous = it) },
        onAllowAddOptionsChange = { uiState = uiState.copy(allowAddOptions = it) },
        onEndDateClick = {}
    )
}

@Preview(name = "생성 - 빈 화면", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun PollCreateScreenEmptyPreview() {
    PollCreateScreenPreviewContent(
        PollCreateUiState(
            endDate = "2026. 4. 14 (화)",
            endTime = "03:15"
        )
    )
}

@Preview(name = "생성 - 입력 후", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun PollCreateScreenFilledPreview() {
    PollCreateScreenPreviewContent(
        PollCreateUiState(
            title = "어쩌구 투표",
            subjects = listOf("Subject1", "Subject2", "Subject3"),
            multipleChoice = true,
            endDate = "2026. 4. 14 (화)",
            endTime = "03:15"
        )
    )
}
