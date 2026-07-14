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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PollTitleInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = DduruTheme.typography.Body1.copy(color = DduruTheme.colors.gray958),
            cursorBrush = SolidColor(DduruTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 13.dp, end = 16.dp, bottom = 13.dp),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "투표 제목을 입력하세요",
                            style = DduruTheme.typography.Body1,
                            color = DduruTheme.colors.gray400
                        )
                    }
                    innerTextField()
                }
            }
        )
        HorizontalDivider(
            color = DduruTheme.colors.gray200,
            thickness = 2.dp
        )
    }
}

@Composable
fun PollSubjectItem(
    value: String,
    onValueChange: (String) -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = DduruTheme.typography.Body1.copy(color = DduruTheme.colors.gray900),
        cursorBrush = SolidColor(DduruTheme.colors.primary),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, DduruTheme.colors.gray200, shape)
            .background(DduruTheme.colors.gray0, shape)
            .padding(start = 16.dp, top = 13.dp, end = 16.dp, bottom = 13.dp),
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = "항목 입력",
                            style = DduruTheme.typography.Body1,
                            color = DduruTheme.colors.gray400
                        )
                    }
                    innerTextField()
                }
                if (value.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .noRippleClickable(onClick = onRemoveClick),
                        tint = DduruTheme.colors.gray400
                    )
                }
            }
        }
    )
}

@Composable
fun PollSubjectList(
    subjects: List<String>,
    onSubjectChange: (Int, String) -> Unit,
    onSubjectRemove: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        subjects.forEachIndexed { index, value ->
            PollSubjectItem(
                value = value,
                onValueChange = { onSubjectChange(index, it) },
                onRemoveClick = { onSubjectRemove(index) }
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DduruTheme.colors.gray200, shape)
                .background(DduruTheme.colors.gray0, shape)
                .padding(start = 16.dp, top = 13.dp, end = 16.dp, bottom = 13.dp)
                .noRippleClickable(onClick = onAddClick)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_write_plus),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = DduruTheme.colors.gray600
            )
            Text(
                text = "선택지 추가",
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.gray600
            )
        }
    }
}

@Composable
fun PollCreateInputSection(
    title: String,
    subjects: List<String>,
    onTitleChange: (String) -> Unit,
    onSubjectChange: (Int, String) -> Unit,
    onSubjectRemove: (Int) -> Unit,
    onAddSubject: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        PollTitleInput(
            value = title,
            onValueChange = onTitleChange
        )
        PollSubjectList(
            subjects = subjects,
            onSubjectChange = onSubjectChange,
            onSubjectRemove = onSubjectRemove,
            onAddClick = onAddSubject
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollCreateInputEmptyPreview() {
    DduruTheme {
        PollCreateInputSection(
            title = "",
            subjects = listOf("", ""),
            onTitleChange = {},
            onSubjectChange = { _, _ -> },
            onSubjectRemove = {},
            onAddSubject = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollCreateInputFilledPreview() {
    DduruTheme {
        PollCreateInputSection(
            title = "어쩌구 투표",
            subjects = listOf("Subject1", "Subject2", "Subject3"),
            onTitleChange = {},
            onSubjectChange = { _, _ -> },
            onSubjectRemove = {},
            onAddSubject = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
