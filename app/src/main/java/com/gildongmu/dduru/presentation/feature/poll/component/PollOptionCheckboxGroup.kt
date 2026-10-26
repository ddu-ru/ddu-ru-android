package com.gildongmu.dduru.presentation.feature.poll.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.OptionalCheckbox

@Composable
fun PollOptionCheckboxGroup(
    multipleChoice: Boolean,
    anonymous: Boolean,
    allowAddOptions: Boolean,
    onMultipleChoiceChange: (Boolean) -> Unit,
    onAnonymousChange: (Boolean) -> Unit,
    onAllowAddOptionsChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        OptionalCheckbox(
            text = "복수 선택 가능",
            checked = multipleChoice,
            onCheckedChange = onMultipleChoiceChange,
            showIconWhenUnchecked = false
        )
        OptionalCheckbox(
            text = "익명 투표",
            checked = anonymous,
            onCheckedChange = onAnonymousChange,
            showIconWhenUnchecked = false
        )
        OptionalCheckbox(
            text = "선택항목 추가 허용",
            checked = allowAddOptions,
            onCheckedChange = onAllowAddOptionsChange,
            showIconWhenUnchecked = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollOptionCheckboxGroupAllUncheckedPreview() {
    DduruTheme {
        PollOptionCheckboxGroup(
            multipleChoice = false,
            anonymous = false,
            allowAddOptions = false,
            onMultipleChoiceChange = {},
            onAnonymousChange = {},
            onAllowAddOptionsChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollOptionCheckboxGroupMixedPreview() {
    DduruTheme {
        PollOptionCheckboxGroup(
            multipleChoice = true,
            anonymous = false,
            allowAddOptions = true,
            onMultipleChoiceChange = {},
            onAnonymousChange = {},
            onAllowAddOptionsChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
