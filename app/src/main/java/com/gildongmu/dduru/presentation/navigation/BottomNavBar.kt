package com.gildongmu.dduru.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    currentRoute: BottomNavRoute?,
    onItemClick: (BottomNavRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = item.key == currentRoute
            BottomNavBarItem(
                item = item,
                isSelected = isSelected,
                onClick = { onItemClick(item.key) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomNavBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColor = DduruTheme.colors.primary
    val inactiveColor = DduruTheme.colors.gray500
    val contentColor = if (isSelected) activeColor else inactiveColor

    val backgroundColor = if (isSelected) DduruTheme.colors.blackAlpha05 else Color.Transparent

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(backgroundColor)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray0.copy(alpha = .3f),
                        shape = RoundedCornerShape(99.dp)
                    )
                } else {
                    Modifier
                }
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = contentColor,
            modifier = Modifier
                .width(18.dp)
                .height(19.dp)
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
private fun BottomNavBarSelectedPreview() {
    val previewBottomNavItems = listOf(
        BottomNavItem(BottomNavRoute.Home, "홈", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Matching, "매칭", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Chat, "채팅", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.MyPage, "마이", R.drawable.ic_bottom_nav_main),
        BottomNavItem(BottomNavRoute.Board, "게시판", R.drawable.ic_bottom_nav_main)
    )
    val items = previewBottomNavItems

    DduruTheme {
        BottomNavBar(
            items = items,
            currentRoute = BottomNavRoute.Home,
            onItemClick = {}
        )
    }
}
