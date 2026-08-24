package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingDestinationField(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray50)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(99.dp)
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "여행지 선택..",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray500
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_matching_searching),
            contentDescription = null,
            tint = DduruTheme.colors.gray500,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun MatchingSegmentedOptionRow(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            MatchingToggleButton(
                text = option,
                selected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun MatchingFilterSmallChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MatchingFilterSelectableChip(
        text = text,
        selected = selected,
        onClick = onClick,
        textStyle = DduruTheme.typography.Caption1,
        shape = RoundedCornerShape(99.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        showUnselectedBorder = false
    )
}

@Composable
private fun MatchingToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MatchingFilterSelectableChip(
        text = text,
        selected = selected,
        onClick = onClick,
        textStyle = DduruTheme.typography.Title2,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.height(48.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 13.dp)
    )
}

@Composable
private fun MatchingFilterSelectableChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    textStyle: TextStyle,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 13.dp),
    showUnselectedBorder: Boolean = true,
    unselectedTextColor: Color = DduruTheme.colors.gray500,
    unselectedBorderColor: Color = DduruTheme.colors.gray200
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(if (selected) DduruTheme.colors.secondary else DduruTheme.colors.gray50)
            .then(
                if (selected || showUnselectedBorder) {
                    Modifier.border(
                        width = 1.dp,
                        color = if (selected) DduruTheme.colors.primary else unselectedBorderColor,
                        shape = shape
                    )
                } else {
                    Modifier
                }
            )
            .noRippleClickable(onClick = onClick)
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if (selected) DduruTheme.colors.primary else unselectedTextColor
        )
    }
}

@Composable
fun MatchingUnderlineInput(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(48.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray400,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(2.dp)
                .background(DduruTheme.colors.gray100)
        )
    }
}
