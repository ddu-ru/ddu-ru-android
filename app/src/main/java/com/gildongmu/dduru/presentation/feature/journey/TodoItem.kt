package com.gildongmu.dduru.presentation.feature.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TodoItem(
    title: String,
    assignees: List<String>,
    modifier: Modifier = Modifier,
    caption: String? = null,
    isCompleted: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .then(
                    if (isCompleted) {
                        Modifier.background(DduruTheme.colors.primary, CircleShape)
                    } else {
                        Modifier
                            .border(1.dp, DduruTheme.colors.gray200, CircleShape)
                            .background(DduruTheme.colors.gray0, CircleShape)
                    }
                )
                .noRippleClickable {
                    onCheckedChange(!isCompleted)
                },
            contentAlignment = Alignment.Center,
        ) {
            if (isCompleted) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.width(10.dp),
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800
            )

            caption?.let {
                Text(
                    text = it,
                    style = DduruTheme.typography.Micro2,
                    color = DduruTheme.colors.gray700
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                assignees.forEach { assignee ->
                    AssigneeChip(
                        name = assignee
                    )
                }
            }
        }
    }
}

@Composable
private fun AssigneeChip(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = DduruTheme.typography.Caption2,
        color = DduruTheme.colors.gray700,
        modifier = modifier
            .background(
                color = DduruTheme.colors.gray100,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun TodoItemPreview() {
    var firstTodoCompleted by remember { mutableStateOf(false) }
    var secondTodoCompleted by remember { mutableStateOf(true) }

    DduruTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TodoItem(
                title = "숙소 예약하기",
                caption = "8월 20일까지",
                assignees = listOf("길동", "둘리"),
                isCompleted = firstTodoCompleted,
                onCheckedChange = { firstTodoCompleted = it },
            )

            TodoItem(
                title = "항공권 예매하기",
                caption = "완료",
                assignees = listOf("길동", "둘리"),
                isCompleted = secondTodoCompleted,
                onCheckedChange = { secondTodoCompleted = it },
            )
        }
    }
}
