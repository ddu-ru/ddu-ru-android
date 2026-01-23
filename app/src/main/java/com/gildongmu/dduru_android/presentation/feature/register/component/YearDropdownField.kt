package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

@Composable
fun YearDropdownField(
    modifier: Modifier = Modifier,
    label: String,
    selectedValue: Int?,
    items: List<Int>,
    onItemSelected: (Int) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val textColor = if (enabled) Color.Black else Color.Black.copy(alpha = 0.4f)

    Column(modifier = modifier) {

        Text(
            text = label,
            style = DduruTheme.typography.BodyMedium,
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            Column {
                val clickableModifier = if (enabled) {
                    Modifier.clickable { expanded = !expanded }
                } else {
                    Modifier
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(clickableModifier)
                        .padding(vertical = 17.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedValue?.let { "%02d".format(it) } ?: "선택",
                        modifier = Modifier.weight(1f),
                        style = DduruTheme.typography.BodyLarge,
                        color = if (selectedValue != null) DduruTheme.colors.Gray900 else DduruTheme.colors.Gray500
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }

                HorizontalDivider(
                    thickness = 2.dp,
                    color = DduruTheme.colors.Gray400
                )
            }

            if (enabled) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .heightIn(max = 480.dp)
                        .background(
                            color = DduruTheme.colors.Gray50,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    items.forEach { item ->
                        val isSelected = (item == selectedValue)

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "%02d".format(item),
                                    color = if (isSelected) DduruTheme.colors.Gray900 else DduruTheme.colors.Gray500,
                                )
                                   },
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
