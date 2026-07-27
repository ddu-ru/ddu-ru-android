package com.gildongmu.dduru.presentation.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    currentRoute: BottomNavRoute?,
    onItemClick: (BottomNavRoute) -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomNavBackground(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        FloatingTabBar(
            items = items,
            currentRoute = currentRoute,
            onItemClick = onItemClick,
            hazeState = hazeState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
        )
    }
}

@Composable
private fun BottomNavBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(108.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(4.dp)
                .background(
                    DduruTheme.colors.gray50.copy(alpha = 0.08f)
                )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.5f to DduruTheme.colors.gray50
                        )
                    )
                )
        )
    }
}

@Composable
private fun FloatingTabBar(
    items: List<BottomNavItem>,
    currentRoute: BottomNavRoute?,
    onItemClick: (BottomNavRoute) -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val containerShape = RoundedCornerShape(99.dp)
    val selectedShape = RoundedCornerShape(99.dp)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(containerShape)
            .hazeChild(
                state = hazeState,
                shape = containerShape,
                style = HazeStyle(
                    tint = DduruTheme.colors.gray50.copy(alpha = 0.18f),
                    blurRadius = 8.dp
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to DduruTheme.colors.gray500.copy(alpha = 0.1f),
                        0.2f to Color.Transparent,
                        0.8f to Color.Transparent,
                        1f to DduruTheme.colors.gray50.copy(alpha = 0.9f)
                    )
                )
            )
            .border(
                width = 1.dp,
                DduruTheme.colors.gray0.copy(alpha = 0.5f),
                shape = containerShape
            )
            .padding(8.dp)
    ) {
        val selectedIndex = items.indexOfFirst { it.route == currentRoute }
            .let { if (it < 0) 0 else it }

        val density = LocalDensity.current
        val tabWidthPx = constraints.maxWidth.toFloat() / items.size.coerceAtLeast(1)
        val indicatorWidth = 73.dp
        val indicatorHeight = 52.dp
        val indicatorWidthPx = with(density) { indicatorWidth.toPx() }

        val indicatorOffsetXPx by animateFloatAsState(
            targetValue = selectedIndex * tabWidthPx + (tabWidthPx - indicatorWidthPx) / 2f,
            animationSpec = spring(
                dampingRatio = 0.82f,
                stiffness = Spring.StiffnessMediumLow
            )
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = indicatorOffsetXPx
                    }
                    .width(indicatorWidth)
                    .height(indicatorHeight)
                    .clip(selectedShape)
                    .background(
                        DduruTheme.colors.black.copy(alpha = 0.05f)
                    )
                    .border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                        shape = selectedShape
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = item.route == currentRoute

                    FloatingTabBarItem(
                        item = item,
                        isSelected = isSelected,
                        onClick = { onItemClick(item.route) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingTabBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColor = DduruTheme.colors.primary
    val inactiveColor = DduruTheme.colors.gray500

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else inactiveColor,
        animationSpec = tween(durationMillis = 220)
    )

    val itemScale by animateFloatAsState(
        targetValue = if (isSelected) 1.03f else 1f,
        animationSpec = spring(
            dampingRatio = 0.85f,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .semantics {
                stateDescription = if (isSelected) "선택됨" else "선택 안 됨"
            }
            .graphicsLayer {
                scaleX = itemScale
                scaleY = itemScale
            }
            .padding(vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.title,
            color = contentColor,
            style = DduruTheme.typography.Micro
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBackgroundPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(DduruTheme.colors.gray958)
    ) {
        BottomNavBackground(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF151515)
@Composable
private fun FloatingTabBarPreview() {
    val previewBottomNavItems = listOf(
        BottomNavItem(BottomNavRoute.Home, "리스트", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Matching, "리스트", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Chat, "리스트", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.MyPage, "리스트", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Board, "리스트", R.drawable.ic_bottom_nav_main)
    )

    val hazeState = remember { HazeState() }

    DduruTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFF151515))
        ) {
            FloatingTabBar(
                items = previewBottomNavItems,
                currentRoute = BottomNavRoute.Chat,
                onItemClick = {},
                hazeState = hazeState,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF151515)
@Composable
private fun BottomNavBarPreview() {
    val previewBottomNavItems = listOf(
        BottomNavItem(BottomNavRoute.Home, "홈", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Matching, "매칭", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Chat, "채팅", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.MyPage, "마이", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Board, "게시판", R.drawable.ic_bottom_nav_main)
    )

    val hazeState = remember { HazeState() }

    DduruTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(DduruTheme.colors.black)
        ) {
            BottomNavBar(
                items = previewBottomNavItems,
                currentRoute = BottomNavRoute.Chat,
                onItemClick = {},
                hazeState = hazeState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
