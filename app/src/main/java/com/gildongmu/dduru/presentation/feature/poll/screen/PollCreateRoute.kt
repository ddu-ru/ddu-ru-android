package com.gildongmu.dduru.presentation.feature.poll.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.gildongmu.dduru.presentation.feature.poll.state.PollCreateUiState

@Composable
fun PollCreateRoute(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var uiState by remember { mutableStateOf(PollCreateUiState()) }

    PollCreateScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onCompleteClick = {
            Toast.makeText(context, "투표 기능은 준비 중이에요.", Toast.LENGTH_SHORT).show()
        },
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
