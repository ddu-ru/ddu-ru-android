package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Text
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.SignupViewModel
import com.gildongmu.dduru_android.presentation.feature.register.state.Gender
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupUiState

@Composable
fun PhoneInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    actionEnabled: Boolean,
    onActionClick: () -> Unit,
    isResendButton: Boolean
) {
    MaterialUnderlineTextField(
        label = "전화번호",
        value = value,
        placeholder = "01012341234",
        onValueChange = onValueChange,
        enabled = enabled,
        actionContent = {
            InlineActionButton(
                text = if (isResendButton) "재전송" else "인증요청",
                enabled = actionEnabled,
                onClick = onActionClick,
                colors = if (isResendButton) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black.copy(alpha = 0.4f)
                    )
                } else {
                    null
                },
                border = if (isResendButton) BorderStroke(1.dp, Color(0xFFD5DAE2)) else null
            )
        }
    )
}

@Composable
fun NicknameInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    onRandomClick: () -> Unit
) {
    MaterialUnderlineTextField(
        label = "닉네임",
        value = value,
        placeholder = "겨울이요길",
        onValueChange = onValueChange,
        enabled = enabled,
        actionContent = {
            RandomNicknameButton(
                enabled = enabled,
                onClick = onRandomClick
            )
        }
    )
}
//
//@Composable
//fun CustomTextField(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit
//) {
//    BasicTextField(
//        value = value,
//        onValueChange = onValueChange
//    )
//}
@Composable
fun AuthCodeInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    actionEnabled: Boolean,
    onActionClick: () -> Unit,
    timerText: String
) {
    MaterialUnderlineTextField(
        label = "인증번호",
        value = value,
        placeholder = timerText,
        onValueChange = onValueChange,
        enabled = enabled,
        actionContent = {
            InlineActionButton(
                text = "인증완료",
                enabled = actionEnabled,
                onClick = onActionClick
            )
        }
    )
}
@Composable
fun BirthInputRow(
    state: SignupUiState,
    viewModel: SignupViewModel,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "년",
            selectedValue = state.year,
            items = viewModel.years,
            onItemSelected = viewModel::onYearSelected,
            enabled = enabled
        )
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "월",
            selectedValue = state.month,
            items = viewModel.months,
            onItemSelected = viewModel::onMonthSelected,
            enabled = enabled
        )
        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "일",
            selectedValue = state.day,
            items = viewModel.days,
            onItemSelected = viewModel::onDaySelected,
            enabled = enabled
        )
    }
}
@Composable
fun GenderSegment(
    selected: Gender?,
    onSelect: (Gender) -> Unit,
    enabled: Boolean
) {
    val unselectedColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = DduruTheme.colors.TextOnButtonDisabled,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = DduruTheme.colors.TextOnButtonDisabled,
//            Color.Black.copy(alpha = 0.4f)
    )
    val buttonShape = RoundedCornerShape(16.dp)

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        val isMaleSelected = selected == Gender.MALE
        PrimaryButton(
            text = "남자",
            enabled = enabled,
            onClick = { onSelect(Gender.MALE) },
            modifier = Modifier
                .weight(1f)
                .let { base ->
                    if (isMaleSelected) base else base.border(
                        width = 1.dp,
                        color =  DduruTheme.colors.MainColor,
                        shape = buttonShape
                    )
                },
            colors = if (isMaleSelected) ButtonDefaults.buttonColors(
                containerColor = DduruTheme.colors.MainColor,
                contentColor = Color.White,
                disabledContainerColor = DduruTheme.colors.MainColor.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            ) else unselectedColors
        )

        val isFemaleSelected = selected == Gender.FEMALE
        PrimaryButton(
            text = "여자",
            enabled = enabled,
            onClick = { onSelect(Gender.FEMALE) },
            modifier = Modifier
                .weight(1f)
                .let { base ->
                    if (isFemaleSelected) base else base.border(
                        width = 1.dp,
                        color =DduruTheme.colors.MainColor,
                        shape = buttonShape
                    )
                },
            colors = if (isFemaleSelected) ButtonDefaults.buttonColors(
                containerColor = DduruTheme.colors.MainColor,
                contentColor = Color.White,
                disabledContainerColor = DduruTheme.colors.MainColor.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            ) else unselectedColors
        )
    }
}

@Composable
fun MaterialUnderlineTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    actionContent: (@Composable (() -> Unit))? = null
) {
    Column {
        val labelColor = if (enabled) Color.Black else Color.Black.copy(alpha = 0.4f)
        val placeholderColor = Color.Black.copy(alpha = if (enabled) 0.4f else 0.2f)

        Text(
            text = label,
            style = DduruTheme.typography.bodyMedium,
            color = labelColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = DduruTheme.typography.bodyMedium,
                        color = placeholderColor
                    )
                },
                textStyle = DduruTheme.typography.bodyMedium,
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = DduruTheme.colors.MainColor,
                    unfocusedIndicatorColor =DduruTheme.colors.MainColor.copy(alpha = 0.4f),
                    disabledIndicatorColor = DduruTheme.colors.MainColor.copy(alpha = 0.2f),

                    cursorColor = DduruTheme.colors.MainColor,
                    focusedTextColor = labelColor,
                    unfocusedTextColor = labelColor
                )
            )

            actionContent?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }
    }
}


@Preview
@Composable
private  fun Screen(){
   MaterialUnderlineTextField(
        label = "전화번호",
        value = "",
        placeholder = "01012341234",
        onValueChange = {}
    )
}

@Composable
fun UnderlineActionTextField(
    label: String,
    value: String,
    placeholder: String,
    actionText: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onActionClick: () -> Unit
) {
    Column {
        Text(
            text = label,
            style = DduruTheme.typography.bodyMedium,
            color = DduruTheme.colors.MainColor
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                textStyle = DduruTheme.typography.bodyMedium,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp) ){
                            if (value.isEmpty()) {
                                Text(text = placeholder, color = DduruTheme.colors.MainColor)
                                innerTextField()
                            }
                        }
                })
        }
        Spacer(modifier = Modifier.height(9.dp))

        OutlinedButton(onClick = onActionClick,enabled = enabled) {
            Text(actionText)
        }
    }
    DividerDefaults
}

@Composable
fun RandomNicknameButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        )
    ) {
        Text(text = "랜덤")
    }
}

@Composable
fun InlineActionButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    colors: ButtonColors? = null,
    border: BorderStroke? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(12.dp),
        border = border,
        colors = colors ?: ButtonDefaults.buttonColors(
            containerColor = if (enabled) DduruTheme.colors.MainColor else Color(0xFFE4E9F0),
            contentColor = if (enabled) Color.White else DduruTheme.colors.MainColor.copy(alpha = 0.5f),
            disabledContainerColor = Color(0xFFE4E9F0),
            disabledContentColor = DduruTheme.colors.MainColor.copy(alpha = 0.4f)
        )
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun RandomNicknameButtonPreview() {
    DduruTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RandomNicknameButton(enabled = true, onClick = {})
            InlineActionButton(text = "인증요청", enabled = true, onClick = {})
            InlineActionButton(
                text = "재전송",
                enabled = true,
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black.copy(alpha = 0.4f)
                ),
                border = BorderStroke(1.dp, Color(0xFFD5DAE2))
            )
        }
    }
}
