package com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private const val SPINNER_DURATION_MILLIS = 1000

@Composable
internal fun SuperhostSheetConfirmButtons(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        SuperhostSheetOutlinedButton(
            text = "취소",
            onClick = onDismiss,
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DduruTheme.colors.primary)
                .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                .noRippleClickable(onClick = onConfirm)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_superhost_ticket),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "1장",
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray0
                )
            }
            Text(
                text = "사용",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Composable
internal fun SuperhostSheetLoadingButton() {
    val rotation by rememberInfiniteTransition(label = "superhostSpinner").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = SPINNER_DURATION_MILLIS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "superhostSpinnerRotation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0.copy(alpha = 0.4f).compositeOver(DduruTheme.colors.primary))
            .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle_notch),
            contentDescription = "처리 중",
            tint = DduruTheme.colors.gray0,
            modifier = Modifier
                .size(24.dp)
                .rotate(rotation)
        )
    }
}

@Composable
internal fun SuperhostSheetCompleteIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        modifier = Modifier.height(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null,
            tint = DduruTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "사용 완료",
            style = DduruTheme.typography.Caption1.copy(lineHeight = 14.4.sp),
            color = DduruTheme.colors.primary
        )
    }
}
