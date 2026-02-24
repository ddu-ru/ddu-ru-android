import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.navigation.BottomNavItem
import com.gildongmu.dduru.presentation.navigation.BottomNavRoute

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
                onClick = { onItemClick(item.key) }
            )
        }
    }
}

@Composable
private fun BottomNavBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = DduruTheme.colors.primary
    val inactiveColor = DduruTheme.colors.gray500
    val contentColor = if (isSelected) activeColor else inactiveColor

    val backgroundColor = if (isSelected) DduruTheme.colors.blackAlpha05 else Color.Transparent

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(99.dp))
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(horizontal = 23.dp, vertical = 7.dp),
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
            style = DduruTheme.typography.Micro,
        )
    }
}
