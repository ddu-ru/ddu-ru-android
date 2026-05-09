package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.core.designsystem.typography.Pretendard
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentCountNum
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentCountUiState

@Composable
fun RecruitmentCount(
    uiState: RecruitmentCountUiState,
    onCountChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CountCircleButton(
                enabled = uiState.canDecrease,
                isPlus = false,
                onClick = { onCountChange(uiState.count - 1) }
            )

            CountText(count = uiState.count)

            CountCircleButton(
                enabled = uiState.canIncrease,
                isPlus = true,
                onClick = { onCountChange(uiState.count + 1) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = uiState.recruitmentCountMessage,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray400,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CountText(
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = count.toString(),
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                lineHeight = 48.sp
            ),
            color = DduruTheme.colors.gray868
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "명",
            style = DduruTheme.typography.Body1,
            color = DduruTheme.colors.gray600
        )
    }
}

@Composable
private fun CountCircleButton(
    enabled: Boolean,
    isPlus: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (enabled) DduruTheme.colors.gray0 else DduruTheme.colors.gray100
    val borderColor = if (enabled) DduruTheme.colors.gray200 else DduruTheme.colors.gray100
    val iconTint = if (enabled) DduruTheme.colors.gray600 else DduruTheme.colors.gray300

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(containerColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            ),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = iconTint,
            disabledContentColor = iconTint
        )
    ) {
        Icon(
            painter = painterResource(
                id = if (isPlus) R.drawable.ic_write_plus else R.drawable.ic_minus
            ),
            contentDescription = null,
            modifier = Modifier.height(17.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RecruitmentCountSelectorPreview() {
    var uiState by remember { mutableStateOf(RecruitmentCountUiState()) }

    DduruTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.padding(vertical = 40.dp)
        ) {
            RecruitmentCount(
                uiState = uiState,
                onCountChange = { newCount ->
                    uiState = uiState.copy(
                        rawCount = newCount.coerceIn(
                            RecruitmentCountNum.MIN,
                            RecruitmentCountNum.MAX
                        )
                    )
                }
            )
        }
    }
}
