package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class ChatSortOption(val label: String) {
    ALL("전체"),
    PRIVATE("1:1 채팅"),
    GROUP("여행방 채팅")
}

@Composable
fun ChatSortDropdown(
    selectedOption: ChatSortOption,
    onOptionSelected: (ChatSortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(
                    color = DduruTheme.colors.gray200,
                    shape = RoundedCornerShape(99.dp)
                )
                .noRippleClickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption.label,
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray700
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_down),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = DduruTheme.colors.gray700
            )
        }

        if (expanded) {
            val offsetY = with(LocalDensity.current) { 44.dp.roundToPx() }
            Popup(
                alignment = Alignment.TopEnd,
                offset = IntOffset(0, offsetY),
                onDismissRequest = { expanded = false }
            ) {
                ChatSortDropdownMenu(
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ChatSortDropdownMenu(
    selectedOption: ChatSortOption,
    onOptionSelected: (ChatSortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DduruTheme.colors.black.copy(alpha = 0.25f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.25f)
            )
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DduruTheme.colors.black.copy(alpha = 0.10f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.10f)
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DduruTheme.colors.black.copy(alpha = 0.08f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.08f)
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                shape = RoundedCornerShape(16.dp)
            )
            .width(120.dp)
            .height(138.dp)
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.80f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)
    ) {
        ChatSortOption.entries.forEach { option ->
            val isSelected = option == selectedOption

            Row(
                horizontalArrangement = if (isSelected) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.spacedBy(8.dp, Alignment.Start)
                },
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(104.dp)
                    .height(30.dp)
                    .noRippleClickable { onOptionSelected(option) }
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = option.label,
                    style = DduruTheme.typography.Caption2,
                    color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray700
                )
                if (isSelected) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = DduruTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatSortDropdownPreview() {
    DduruTheme {
        var selected by remember { mutableStateOf(ChatSortOption.ALL) }
        ChatSortDropdown(
            selectedOption = selected,
            onOptionSelected = { selected = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatSortDropdownMenuPreview() {
    DduruTheme {
        ChatSortDropdownMenu(
            selectedOption = ChatSortOption.ALL,
            onOptionSelected = {}
        )
    }
}
