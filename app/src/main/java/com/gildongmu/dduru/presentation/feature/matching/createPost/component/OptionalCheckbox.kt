package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun OptionalCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    showIconWhenUnchecked: Boolean = true
) {
    val iconBackgroundColor = if (checked) {
        DduruTheme.colors.primary
    } else {
        DduruTheme.colors.gray200
    }

    val textColor = if (checked) {
        DduruTheme.colors.primary
    } else {
        DduruTheme.colors.gray300
    }

    val checkIconColor = if (checked) {
        DduruTheme.colors.gray0
    } else {
        DduruTheme.colors.gray500
    }

    val boxModifier = if (!checked && !showIconWhenUnchecked) {
        Modifier
            .size(20.dp)
            .border(1.25.dp, DduruTheme.colors.gray300, CircleShape)
            .background(DduruTheme.colors.gray0, CircleShape)
    } else {
        Modifier
            .size(20.dp)
            .background(color = iconBackgroundColor, shape = CircleShape)
    }

    val resolvedTextColor = if (!checked && !showIconWhenUnchecked) {
        DduruTheme.colors.gray500
    } else {
        textColor
    }

    Row(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onCheckedChange(!checked)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            if (checked || showIconWhenUnchecked) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = checkIconColor,
                    modifier = Modifier.width(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = DduruTheme.typography.Title2,
            color = resolvedTextColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun OptionalCheckboxPreview() {
    var checkedCo by remember { mutableStateOf(false) }
    var checkedAge by remember { mutableStateOf(false) }

    Column {
        OptionalCheckbox(
            checked = checkedCo,
            onCheckedChange = { checkedCo = it },
            modifier = Modifier.padding(16.dp),
            text = "동행 형태는 상관 없어요"
        )

        Spacer(modifier = Modifier.height(24.dp))

        OptionalCheckbox(
            checked = checkedAge,
            onCheckedChange = { checkedAge = it },
            modifier = Modifier.padding(16.dp),
            text = "나이는 상관 없어요"
        )
    }
}
