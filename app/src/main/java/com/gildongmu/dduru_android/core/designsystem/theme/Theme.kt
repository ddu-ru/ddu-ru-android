package com.gildongmu.dduru_android.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

object DduruTheme {
    val colors: DduruColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDduruColors.current

    val typography: DduruTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDduruTypography.current
}

@Composable
fun DduruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = defaultDduruColors
    val typography = defaultDduruTypography

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            @Suppress("DEPRECATION")
            window.statusBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        LocalDduruColors provides colors,
        LocalDduruTypography provides typography
    ) {
        MaterialTheme(
            content = content
        )
    }
}
