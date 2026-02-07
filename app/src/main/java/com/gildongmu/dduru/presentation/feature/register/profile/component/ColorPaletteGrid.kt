package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorDotGrid(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    columnCount: Int = 8,
    selectedColor: Color? = null,
    onColorClick: (Color) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columnCount),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(0.dp),
        userScrollEnabled = false
    ) {
        items(colors) { color ->
            val isSelected = selectedColor != null && color == selectedColor
            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(color, CircleShape)
                        .clickable { onColorClick(color) }
                        .then(
                            if (isSelected) {
                                Modifier.border(2.dp, Color.White, CircleShape)
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
    }
}
