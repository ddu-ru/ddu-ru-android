package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList

@Composable
fun ColorDotGrid(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    columnCount: Int = 8,
    selectedColor: Color? = null,
    onColorClick: (Color) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columnCount),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
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
                        .size(48.dp)
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

@Preview(showBackground = true)
@Composable
fun ColorDotGridPreview() {
    var selectedColor by remember { mutableStateOf<Color?>(ProfileBackgroundColorList.first().color) }

    ColorDotGrid(
        modifier = Modifier.padding(bottom = 78.dp),
        colors = ProfileBackgroundColorList.map { it.color },
        selectedColor = selectedColor,
        onColorClick = {
                clickedColor ->
            selectedColor = clickedColor
        }
    )
}
