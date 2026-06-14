package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class PostStatus(
    val label: String
) {
    RECRUITING("모집중"),
    CLOSED("모집 마감"),
    HIDDEN("숨김")
}

@Composable
fun PostStatusDropdown(
    selectedStatus: PostStatus,
    onStatusSelected: (PostStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 1.dp,
                color = DduruTheme.colors.gray200
            ),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = selectedStatus.label,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = DduruTheme.colors.gray800,
                contentDescription = null
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(color = DduruTheme.colors.gray0.copy(alpha = 0.7f))
                .wrapContentWidth(),
            border = BorderStroke(
                width = 1.dp,
                color =  DduruTheme.colors.gray0.copy(alpha = 0.3f)
            ),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(16.dp)
        ) {
            PostStatus.entries.forEach { status ->
                DropdownMenuItem(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = status.label,
                                style = DduruTheme.typography.Caption2,
                                color = if (status == selectedStatus) {
                                    DduruTheme.colors.primary
                                } else {
                                    DduruTheme.colors.gray700
                                }
                            )

                            if (status == selectedStatus) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = DduruTheme.colors.primary,
                                    modifier = Modifier.width(12.dp)
                                )
                            }
                        }
                    },

                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostStatusDropdownPreview() {
    DduruTheme {
        var status by remember {
            mutableStateOf(PostStatus.RECRUITING)
        }

        PostStatusDropdown(
            selectedStatus = status,
            onStatusSelected = { status = it },
        )
    }
}
