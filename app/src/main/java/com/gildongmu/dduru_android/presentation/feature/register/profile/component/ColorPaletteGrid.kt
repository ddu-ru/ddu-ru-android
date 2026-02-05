import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color

@Composable
fun ColorDotGrid(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    columnCount: Int = 8,
    selectedColor: Color? = null,
    onColorClick: (Color) -> Unit = {}
) {
    val remaining = if (colors.isEmpty()) 0
    else if (colors.size % columnCount == 0) 0
    else columnCount - (colors.size % columnCount)

    val totalSlots = colors.size + remaining

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = columnCount
    ) {
        for (i in 0 until totalSlots) {
            val color = colors.getOrNull(i) ?: Color.Transparent
            val isSelected = selectedColor != null && color == selectedColor

            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(color, CircleShape)
                        .then(
                            if (color != Color.Transparent)
                                Modifier.clickable { onColorClick(color) }
                            else Modifier
                        )
                        .then(
                            if (isSelected)
                                Modifier.border(2.dp, Color.White, CircleShape)
                            else Modifier
                        )
                )
            }
        }
    }
}