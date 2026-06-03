package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingCreatePostScaffold(
    currentStep: Int,
    modifier: Modifier = Modifier,
    region: String? = null,
    country: String? = null,
    isNextEnabled: Boolean = false,
    onDismissClick: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val isNowStepOne = currentStep == 1
    val nextButtonText = if (currentStep == 6) "작성 완료" else "다음"

    Column(
        modifier = modifier
            .statusBarsPadding()
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onDismissClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(99.dp),
                        spotColor = DduruTheme.colors.black.copy(alpha = 0.8f),
                        ambientColor = DduruTheme.colors.black.copy(alpha = 0.8f)
                    )
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(99.dp),
                        spotColor = DduruTheme.colors.black.copy(alpha = 0.1f),
                        ambientColor = DduruTheme.colors.black.copy(alpha = 0.1f)
                    )
                    .border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(99.dp)
                    )
                    .background(
                        color = DduruTheme.colors.gray0,
                        shape = RoundedCornerShape(99.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = DduruTheme.colors.black
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "여행방 생성",
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.black,
                )

                if (region != null || country != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        region?.let {
                            Text(
                                text = it,
                                style = DduruTheme.typography.Micro,
                                color = DduruTheme.colors.gray400,
                            )
                        }

                        if (region != null && country != null) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(DduruTheme.colors.gray200, CircleShape)
                            )
                        }

                        country?.let {
                            Text(
                                text = it,
                                style = DduruTheme.typography.Micro,
                                color = DduruTheme.colors.gray400,
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier.height(24.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            content()
        }

        if (!isNowStepOne) {
            Row(
                modifier = Modifier.height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "이전",
                    onClick = onBackClick,
                    enabledColors = DduruButtonDefaults.whiteEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    modifier = Modifier.weight(1f)
                )

                DduruButton(
                    text = nextButtonText,
                    onClick = onNextClick,
                    enabled = isNextEnabled,
                    enabledColors = DduruButtonDefaults.filledEnabledColors,
                    disabledColors = DduruButtonDefaults.whiteDisabledColors,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier  = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MatchingCreatePostScaffoldStepOnePreview() {
    DduruTheme {
        MatchingCreatePostScaffold(
            currentStep = 1,
            region = "일본",
            country = "도쿄",
            isNextEnabled = true,
            onDismissClick = {},
            onBackClick = {},
            onNextClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DduruTheme.colors.gray50, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "step1",
                    style = DduruTheme.typography.Title1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatchingCreatePostScaffoldPreview() {
    DduruTheme {
        MatchingCreatePostScaffold(
            currentStep = 2,
            region = "일본",
            country = "도쿄",
            isNextEnabled = true,
            onDismissClick = {},
            onBackClick = {},
            onNextClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DduruTheme.colors.gray50, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "step2~5",
                    style = DduruTheme.typography.Title1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatchingCreatePostScaffoldStepSixPreview() {
    DduruTheme {
        MatchingCreatePostScaffold(
            currentStep = 6,
            region = "일본",
            country = "도쿄",
            isNextEnabled = true,
            onDismissClick = {},
            onBackClick = {},
            onNextClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DduruTheme.colors.gray50, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "step6",
                    style = DduruTheme.typography.Title1
                )
            }
        }
    }
}
