package com.gildongmu.dduru.presentation.feature.chat.component

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
fun PollOptionCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.noRippleClickable { onCheckedChange(!checked) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(20.dp)
                .then(
                    if (checked) {
                        Modifier.background(DduruTheme.colors.primary, CircleShape)
                    } else {
                        Modifier
                            .border(1.25.dp, DduruTheme.colors.gray300, CircleShape)
                            .background(DduruTheme.colors.gray0, CircleShape)
                    }
                )
                .padding(start = 5.dp, top = 6.25.dp, end = 5.dp, bottom = 6.25.dp)
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(10.dp)
                )
            }
        }
        Text(
            text = label,
            style = DduruTheme.typography.Title2,
            color = if (checked) DduruTheme.colors.primary else DduruTheme.colors.gray500
        )
    }
}

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
        PollOptionCheckbox(
            label = "복수 선택 가능",
            checked = multipleChoice,
            onCheckedChange = onMultipleChoiceChange
        )
        PollOptionCheckbox(
            label = "익명 투표",
            checked = anonymous,
            onCheckedChange = onAnonymousChange
        )
        PollOptionCheckbox(
            label = "선택항목 추가 허용",
            checked = allowAddOptions,
            onCheckedChange = onAllowAddOptionsChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollOptionCheckboxUncheckedPreview() {
    DduruTheme {
        PollOptionCheckbox(
            label = "복수 선택 가능",
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollOptionCheckboxCheckedPreview() {
    DduruTheme {
        PollOptionCheckbox(
            label = "복수 선택 가능",
            checked = true,
            onCheckedChange = {},
            modifier = Modifier.padding(16.dp)
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
