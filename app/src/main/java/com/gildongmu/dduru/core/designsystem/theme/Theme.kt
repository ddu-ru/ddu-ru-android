package com.gildongmu.dduru.core.designsystem.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.gildongmu.dduru.core.designsystem.color.DduruColors
import com.gildongmu.dduru.core.designsystem.color.LocalDduruColors
import com.gildongmu.dduru.core.designsystem.color.defaultDduruColors
import com.gildongmu.dduru.core.designsystem.typography.DduruTypography
import com.gildongmu.dduru.core.designsystem.typography.LocalDduruTypography
import com.gildongmu.dduru.core.designsystem.typography.defaultDduruTypography

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
    val isInPreview = LocalInspectionMode.current
    if (!view.isInEditMode && !isInPreview) {
        SideEffect {
            val window = view.context.findActivity()?.window ?: return@SideEffect

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

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
