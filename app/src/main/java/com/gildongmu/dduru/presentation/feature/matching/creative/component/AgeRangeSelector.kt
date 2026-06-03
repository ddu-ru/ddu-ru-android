package com.gildongmu.dduru.presentation.feature.matching.creative.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun AgeRangeBar(
    selectedStart: Int?,
    selectedEnd: Int?,
    onRangeChanged: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isAgeIrrelevant: Boolean = false
) {
    val ages = listOf(20, 25, 30, 35, 40, 45, 50, 55, 60)

    val primary = DduruTheme.colors.primary
    val secondary = DduruTheme.colors.secondary
    val gray0 = DduruTheme.colors.gray0
    val gray100 = DduruTheme.colors.gray100
    val gray200 = DduruTheme.colors.gray200
    val gray300 = DduruTheme.colors.gray300

    val capWidth = 24.dp
    val barHeight = 48.dp
    val labelWidth = 36.dp

    val density = LocalDensity.current

    var containerWidthPx by remember { mutableFloatStateOf(0f) }
    var dragStartIndex by remember { mutableStateOf<Int?>(null) }

    val startIndex = selectedStart?.let { ages.indexOf(it) } ?: -1
    val endIndex = selectedEnd?.let { ages.indexOf(it) } ?: -1

    val ageRangeText = if (isAgeIrrelevant) {
        "연령무관"
    } else if (selectedStart != null && selectedEnd != null) {
        "$selectedStart-${selectedEnd}세"
    } else {
        "선택해주세요"
    }

    fun segmentWidthPx(containerWidthPx: Float, capWidthPx: Float): Float {
        return ((containerWidthPx - capWidthPx * 2) / (ages.size - 1))
            .coerceAtLeast(0f)
    }

    fun tickX(
        index: Int,
        capWidthPx: Float,
        segmentWidthPx: Float
    ): Float {
        return capWidthPx + segmentWidthPx * index
    }

    fun xToIndex(x: Float): Int {
        if (containerWidthPx <= 0f) return 0

        val capWidthPx = with(density) { capWidth.toPx() }
        val segmentWidthPx = segmentWidthPx(containerWidthPx, capWidthPx)

        if (segmentWidthPx <= 0f) return 0

        return floor((x - capWidthPx) / segmentWidthPx)
            .toInt()
            .coerceIn(0, ages.lastIndex - 1)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "함께하고 싶은 연령대",
                modifier = Modifier.weight(1f),
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800
            )

            Text(
                text = ageRangeText,
                style = DduruTheme.typography.Title3,
                color = if (isAgeIrrelevant || (selectedStart != null && selectedEnd != null)) {
                    primary
                } else {
                    DduruTheme.colors.gray500
                },
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { containerWidthPx = it.width.toFloat() }
                .let { baseModifier ->
                    if (!enabled) {
                        baseModifier
                    } else {
                        baseModifier
                            .pointerInput(enabled, onRangeChanged) {
                                detectTapGestures(
                                    onTap = { offset ->
                                        val index = xToIndex(offset.x)
                                        onRangeChanged(ages[index], ages[index + 1])
                                    }
                                )
                            }
                            .pointerInput(enabled, onRangeChanged) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        val index = xToIndex(offset.x)
                                        dragStartIndex = index
                                    },
                                    onDrag = { change, _ ->
                                        val start = dragStartIndex ?: return@detectDragGestures
                                        val current = xToIndex(change.position.x)

                                        val from = minOf(start, current)
                                        val to = maxOf(start, current)

                                        onRangeChanged(ages[from], ages[to + 1])
                                    },
                                    onDragEnd = { dragStartIndex = null },
                                    onDragCancel = { dragStartIndex = null }
                                )
                            }
                    }
                }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(barHeight)
                ) {
                    val capWidthPx = capWidth.toPx()
                    val barHeightPx = barHeight.toPx()
                    val segmentWidthPx = segmentWidthPx(size.width, capWidthPx)

                    val barPath = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    left = 0f,
                                    top = 0f,
                                    right = size.width,
                                    bottom = barHeightPx
                                ),
                                topLeft = CornerRadius(barHeightPx / 2f),
                                bottomLeft = CornerRadius(barHeightPx / 2f),
                                topRight = CornerRadius(barHeightPx / 2f),
                                bottomRight = CornerRadius(barHeightPx / 2f)
                            )
                        )
                    }

                    drawPath(
                        path = barPath,
                        color = if (isAgeIrrelevant) gray100 else gray0
                    )

                    if (startIndex >= 0 && endIndex >= 0 && !isAgeIrrelevant) {
                        val selectedLeftXPx = tickX(
                            index = startIndex,
                            capWidthPx = capWidthPx,
                            segmentWidthPx = segmentWidthPx
                        )

                        val selectedRightXPx = tickX(
                            index = endIndex,
                            capWidthPx = capWidthPx,
                            segmentWidthPx = segmentWidthPx
                        )

                        clipPath(barPath) {
                            drawRect(
                                color = secondary,
                                topLeft = Offset(
                                    x = selectedLeftXPx,
                                    y = 0f
                                ),
                                size = Size(
                                    width = selectedRightXPx - selectedLeftXPx,
                                    height = barHeightPx
                                )
                            )
                        }
                    }

                    ages.forEachIndexed { index, _ ->
                        val x = tickX(
                            index = index,
                            capWidthPx = capWidthPx,
                            segmentWidthPx = segmentWidthPx
                        )

                        drawLine(
                            color = if (isAgeIrrelevant) gray200 else gray300,
                            start = Offset(x, 0f),
                            end = Offset(x, barHeightPx),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    drawPath(
                        path = barPath,
                        color = if (isAgeIrrelevant) gray200 else gray300,
                        style = Stroke(width = 1.dp.toPx())
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    if (containerWidthPx > 0f) {
                        val capWidthPx = with(density) { capWidth.toPx() }
                        val segmentWidthPx = segmentWidthPx(containerWidthPx, capWidthPx)
                        val labelWidthPx = with(density) { labelWidth.toPx() }

                        ages.forEachIndexed { index, age ->
                            val centerXPx = tickX(
                                index = index,
                                capWidthPx = capWidthPx,
                                segmentWidthPx = segmentWidthPx
                            )

                            val isSelected = selectedStart != null &&
                                selectedEnd != null &&
                                age in selectedStart..selectedEnd

                            Text(
                                text = if (age == 60) "60+" else age.toString(),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .offset {
                                        IntOffset(
                                            x = (centerXPx - labelWidthPx / 2f).roundToInt(),
                                            y = 0
                                        )
                                    }
                                    .width(labelWidth)
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                                style = DduruTheme.typography.Caption2,
                                color = if (isAgeIrrelevant) {
                                    DduruTheme.colors.gray400
                                } else if (isSelected) {
                                    primary
                                } else {
                                    DduruTheme.colors.gray500
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgeRangeBarPreview() {
    DduruTheme {
        var selectedStart by remember { mutableStateOf<Int?>(null) }
        var selectedEnd by remember { mutableStateOf<Int?>(null) }

        AgeRangeBar(
            selectedStart = selectedStart,
            selectedEnd = selectedEnd,
            onRangeChanged = { start, end ->
                selectedStart = start
                selectedEnd = end
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
