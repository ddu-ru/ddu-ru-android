package gildong.dduru.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

// 2. Provider 함수
@Composable
fun DduruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 컬러랑 폰트 기본값 가져오기
    val colors = defaultDduruColors
    val typography = defaultDduruTypography

    // 상태바(Status Bar) 제어
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // 상태바 배경 투명
            @Suppress("DEPRECATION")
            window.statusBarColor = Color.Transparent.toArgb()

            // 상태바 아이콘 검정색
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    // 3. CompositionLocalProvider로 주입
    CompositionLocalProvider(
        LocalDduruColors provides colors,
        LocalDduruTypography provides typography
    ) {
        MaterialTheme(
            content = content
        )
    }
}
