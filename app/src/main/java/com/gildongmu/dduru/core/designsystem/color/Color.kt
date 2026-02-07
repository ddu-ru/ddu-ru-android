package com.gildongmu.dduru.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF289BFF)
val Secondary = Color(0xFFC8E5FF)
val Secondary100 = Color(0xFFE3F2FF)

val Gray0 = Color(0xFFFFFFFF) // White는 Gray0으로 정의
val Gray50 = Color(0xFFF8F9FA)
val Gray100 = Color(0xFFF1F3F4)
val Gray200 = Color(0xFFE8EAED)
val Gray300 = Color(0xFFDADCE0)
val Gray400 = Color(0xFFBDC1C6)
val Gray500 = Color(0xFF9AA0A6)
val Gray600 = Color(0xFF80868B)
val Gray700 = Color(0xFF5F6368)
val Gray800 = Color(0xFF3C4043)
val Gray846 = Color(0xFF2E3134)
val Gray868 = Color(0xFF282A2D)
val Gray900 = Color(0xFF202124)
val Gray928 = Color(0xFF17181B)
val Gray958 = Color(0xFF0E1013)
val Black = Color(0xFF000000)
val KakaoYellow = Color(0xFFFEE500)
val BlackAlpha14 = Color(0x24000000)
val BlackAlpha10 = Color(0x1A000000)
val BlackAlpha05 = Color(0x0D000000)
val WhiteAlpha30 = Color(0x4DFFFFFF)
val GrayBorder = Color(0x80797479)

@Immutable
data class DduruColors(

    val primary: Color,
    val secondary: Color,
    val secondary100: Color,

    val gray0: Color,
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray846: Color,
    val gray868: Color,
    val gray900: Color,
    val gray928: Color,
    val gray958: Color,

    val black: Color,

    val kakaoYellow: Color,
    val blackAlpha14: Color,
    val blackAlpha10: Color,
    val blackAlpha05: Color,
    val whiteAlpha30: Color,
    val grayBorder: Color
)

val defaultDduruColors = DduruColors(
    primary = Primary,
    secondary = Secondary,
    secondary100 = Secondary100,

    // GrayScale
    gray0 = Gray0,
    gray50 = Gray50,
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    gray700 = Gray700,
    gray800 = Gray800,
    gray846 = Gray846,
    gray868 = Gray868,
    gray900 = Gray900,
    gray928 = Gray928,
    gray958 = Gray958,

    black = Black,

    kakaoYellow = KakaoYellow,

    blackAlpha14 = BlackAlpha14,
    blackAlpha10 = BlackAlpha10,
    blackAlpha05 = BlackAlpha05,
    whiteAlpha30 = WhiteAlpha30,
    grayBorder = GrayBorder
)

val LocalDduruColors = staticCompositionLocalOf { defaultDduruColors }
