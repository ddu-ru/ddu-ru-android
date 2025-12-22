package com.gildongmu.dduru_android.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun LoginButton(
    @DrawableRes iconResId: Int,
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasBorder: Boolean = false // 구글 버튼 등 테두리가 필요할 때를 대비
) {
    val buttonShape = RoundedCornerShape(16.dp)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .shadow(
                elevation = 8.dp,
                shape = buttonShape,
                spotColor = DduruTheme.colors.BlackAlpha10,
                ambientColor = DduruTheme.colors.BlackAlpha10
            )
            .then(
                if (hasBorder) Modifier.border(
                    width = 0.8.dp,
                    color = DduruTheme.colors.GrayBorder,
                    shape = buttonShape
                ) else Modifier
            )
            .clip(buttonShape)
            .clickable { onClick() },
        color = containerColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 25.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            // 아이콘
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = text,
                color = contentColor,
                style = DduruTheme.typography.Button
            )
        }
    }
}
