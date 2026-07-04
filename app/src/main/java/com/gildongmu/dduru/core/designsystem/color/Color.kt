package com.gildongmu.dduru.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF289BFF)
val Secondary = Color(0xFFC8E5FF)
val Secondary100 = Color(0xFFE3F2FF)

val Gray0 = Color(0xFFFFFFFF)
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
val GrayNeutral = Color(0xFF969696)
val GrayBorder = Color(0xFF797479)
val Gray9E = Color(0xFF9E9E9E)
val Black = Color(0xFF000000)
val ValidationRed = Color(0xFFFF2828)
val KakaoYellow = Color(0xFFFEE500)
val BadgeOrange = Color(0xFFFF6C28)
val AcceptGreen = Color(0xFF2A9669)
val AcceptGreenBg = Color(0xFFC8F2DE)
val RejectRed = Color(0xFFEF4343)
val RejectRedBg = Color(0xFFF6E1E1)
val RejectConfirmBg = Color(0xFFFFE9E9)

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
    val grayNeutral: Color,
    val grayBorder: Color,
    val gray9E: Color,

    val black: Color,

    val validationRed: Color,
    val kakaoYellow: Color,
    val badgeOrange: Color,
    val acceptGreen: Color,
    val acceptGreenBg: Color,
    val rejectRed: Color,
    val rejectRedBg: Color,
    val rejectConfirmBg: Color
)

val defaultDduruColors = DduruColors(
    primary = Primary,
    secondary = Secondary,
    secondary100 = Secondary100,

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
    grayNeutral = GrayNeutral,
    grayBorder = GrayBorder,
    gray9E = Gray9E,

    black = Black,

    kakaoYellow = KakaoYellow,
    validationRed = ValidationRed,
    badgeOrange = BadgeOrange,
    acceptGreen = AcceptGreen,
    acceptGreenBg = AcceptGreenBg,
    rejectRed = RejectRed,
    rejectRedBg = RejectRedBg,
    rejectConfirmBg = RejectConfirmBg
)

val LocalDduruColors = staticCompositionLocalOf { defaultDduruColors }
