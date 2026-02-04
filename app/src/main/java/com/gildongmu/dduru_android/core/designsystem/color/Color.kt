package com.gildongmu.dduru_android.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 1. Reference Token
val Primary= Color(0xFF289BFF)
val Secondary = Color(0xFFC8E5FF)

val Gray0   = Color(0xFFFFFFFF) // White
val Gray50  = Color(0xFFF8F9FA)
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
val BlackAlpha25 = Color(0x40000000)
val GrayBorder = Color(0x80797479)

// 2. System Token
@Immutable
data class DduruColors(
    // Brand
    val Primary: Color,
    val Secondary: Color,

    // GrayScale
    val Gray0: Color,
    val Gray50: Color,
    val Gray100: Color,
    val Gray200: Color,
    val Gray300: Color,
    val Gray400: Color,
    val Gray500: Color,
    val Gray600: Color,
    val Gray700: Color,
    val Gray800: Color,
    val Gray846: Color,
    val Gray868: Color,
    val Gray900: Color,
    val Gray928: Color,
    val Gray958: Color,

    val Black: Color,

    val KakaoYellow: Color,
    val BlackAlpha14: Color,
    val BlackAlpha10: Color,
    val BlackAlpha05: Color,
    val WhiteAlpha30: Color,
    val BlackAlpha25: Color,
    val GrayBorder : Color
)



//3.매핑

val defaultDduruColors = DduruColors(
    Primary = Primary,
    Secondary = Secondary,

    // GrayScale
    Gray0 = Gray0,
    Gray50 = Gray50,
    Gray100 = Gray100,
    Gray200 = Gray200,
    Gray300 = Gray300,
    Gray400 = Gray400,
    Gray500 = Gray500,
    Gray600 = Gray600,
    Gray700 = Gray700,
    Gray800 = Gray800,
    Gray846 = Gray846,
    Gray868 = Gray868,
    Gray900 = Gray900,
    Gray928 = Gray928,
    Gray958 = Gray958,

    Black = Black,

    KakaoYellow = KakaoYellow,
    BlackAlpha14 = BlackAlpha14,
    BlackAlpha10 = BlackAlpha10,
    BlackAlpha05 = BlackAlpha05,
    WhiteAlpha30 = WhiteAlpha30,
    BlackAlpha25 = BlackAlpha25,
    GrayBorder = GrayBorder
)

val LocalDduruColors = staticCompositionLocalOf { defaultDduruColors }
