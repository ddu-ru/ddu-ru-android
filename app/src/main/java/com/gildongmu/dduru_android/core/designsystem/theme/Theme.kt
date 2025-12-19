package com.gildongmu.dduru_android.core.designsystem.theme


import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.gildongmu.dduru_android.core.designsystem.color.Black
import com.gildongmu.dduru_android.core.designsystem.color.DduruColors
import com.gildongmu.dduru_android.core.designsystem.color.DeepOrange
import com.gildongmu.dduru_android.core.designsystem.color.LocalDduruColors
import com.gildongmu.dduru_android.core.designsystem.color.Orange100
import com.gildongmu.dduru_android.core.designsystem.color.Orange500
import com.gildongmu.dduru_android.core.designsystem.color.White
import com.gildongmu.dduru_android.core.designsystem.color.defaultDduruColors
import com.gildongmu.dduru_android.core.designsystem.typography.DduruTypography
import com.gildongmu.dduru_android.core.designsystem.typography.LocalDduruTypography
import com.gildongmu.dduru_android.core.designsystem.typography.defaultDduruTypography


// 1. Theme Object
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

// 2. Material3 호환을 위한 ColorScheme
private val LightColorScheme = lightColorScheme(
    primary = Orange500,
    onPrimary = White,
    secondary = Orange100,
    onSecondary = Black,
    background = White,
    surface = White,
    onBackground = Black,
    onSurface = Black,
    error = DeepOrange
)

// 3. Theme Provider
@Composable
fun DduruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color는 끄는 게 디자인 시스템 유지에 좋습니다.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // 1. 우리가 만든 커스텀 컬러/폰트 가져오기
    val colors = defaultDduruColors
    val typography = defaultDduruTypography

    // 2. 상태바 색상 설정
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.Background.toArgb() // 배경색과 맞춤
            // 상태바 아이콘 색상
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // 3. CompositionLocalProvider
    CompositionLocalProvider(
        LocalDduruColors provides colors,
        LocalDduruTypography provides typography
    ) {
        // 4. MaterialTheme 호환성 유지
        MaterialTheme(
            colorScheme = LightColorScheme, // Material 컴포넌트용
            content = content
        )
    }
}
