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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun ColorDotGrid(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    columnCount: Int = 9,
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
            Box(
                modifier = Modifier
                    .weight(1f / columnCount),
                contentAlignment = Alignment.Center
            ) {

                val color = colors.getOrNull(i) ?: Color.Transparent

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}
