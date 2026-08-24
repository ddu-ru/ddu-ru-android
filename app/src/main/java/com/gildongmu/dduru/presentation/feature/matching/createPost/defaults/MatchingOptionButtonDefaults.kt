package com.gildongmu.dduru.presentation.feature.matching.createPost.defaults

import androidx.compose.runtime.Composable
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDisabledColors
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

object MatchingOptionButtonDefaults {
    val selectedColors: DduruButtonEnabledColors
        @Composable
        get() = DduruButtonEnabledColors(
            backgroundColor = DduruTheme.colors.secondary,
            textColor = DduruTheme.colors.primary,
            borderColor = DduruTheme.colors.primary
        )

    val unselectedColors: DduruButtonEnabledColors
        @Composable
        get() = DduruButtonEnabledColors(
            backgroundColor = DduruTheme.colors.gray0,
            textColor = DduruTheme.colors.gray500,
            borderColor = DduruTheme.colors.gray200
        )

    val disabledColors: DduruButtonDisabledColors
        @Composable
        get() = DduruButtonDisabledColors(
            backgroundColor = DduruTheme.colors.gray200,
            textColor = DduruTheme.colors.gray400,
            borderColor = DduruTheme.colors.gray300
        )
}
