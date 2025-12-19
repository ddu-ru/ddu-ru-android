package com.gildongmu.dduru_android.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 1. Reference Token

val Orange500 = Color(0xFFFFA100) // 메인 주황
val Orange100 = Color(0xFFFFECCA) // 연한 주황
val DeepOrange = Color(0xFFF36945) // 포인트 테두리
val SoftOrange = Color(0xFFFF9A80) // 그라데이션용 살구색
val Yellow500 = Color(0xFFFEE500) // 그라데이션용 노란색
val PaleYellow = Color(0xFFFFFCEE) // 크림색 배경


val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// Gray Scale
val Gray958 = Color(0xFF0E1013)
val Gray928 = Color(0xFF17181B)
val Gray900 = Color(0xFF202124)
val Gray868 = Color(0xFF282A2D)
val Gray846 = Color(0xFF2E3134)
val Gray800 = Color(0xFF3C4043)
val Gray700 = Color(0xFF5F6368)
val Gray600 = Color(0xFF80868B)
val Gray500 = Color(0xFF9AA0A6)
val Gray400 = Color(0xFFBDC1C6)
val Gray300 = Color(0xFFDADCE0)
val Gray200 = Color(0xFFE8EAED)
val Gray100 = Color(0xFFF1F3F4)
val Gray50  = Color(0xFFF8F9FA)

// 2. System Token

@Immutable
data class DduruColors(
    // Brand Identity
    val MainColor: Color,
    val SubColor: Color,

    // Actions & Interactive
    val ButtonActive: Color,
    val ButtonDisabled: Color,
    val TextOnButtonActive: Color,
    val TextOnButtonDisabled: Color,

    // Selection & Toggles
    val SelectActiveBg: Color,
    val SelectActiveText: Color,
    val SelectInactiveBg: Color,
    val SelectInactiveBorder: Color,
    val SelectInactiveText: Color,

    // Components (Badge, Highlight)
    val StepBadgeBg: Color,
    val StepBadgeText: Color,
    val PointBorder: Color,
    val HighlightBg: Color,

    // Backgrounds
    val Background: Color,
    val Surface: Color,

    // Texts & Icons
    val TextMain: Color,
    val TextBody: Color,
    val TextSub: Color,
    val TextHint: Color,
    val IconDefault: Color,

    // Borders & Lines
    val BorderDefault: Color,
    val LineDivider: Color,

    // Gradients
    val GradientStart: Color,
    val GradientEnd: Color
)


// 3. 기본값 연결

val defaultDduruColors = DduruColors(
    MainColor = Orange500,
    SubColor = Orange100,

    ButtonActive = Orange500,
    ButtonDisabled = Gray200,
    TextOnButtonActive = White,
    TextOnButtonDisabled = Gray500,

    SelectActiveBg = Orange500,
    SelectActiveText = White,
    SelectInactiveBg = White,
    SelectInactiveBorder = Gray300,
    SelectInactiveText = Gray600,

    StepBadgeBg = Orange100,
    StepBadgeText = Orange500,

    PointBorder = DeepOrange,
    HighlightBg = PaleYellow,

    Background = White,
    Surface = White,

    TextMain = Gray900,
    TextBody = Gray700,
    TextSub = Gray600,
    TextHint = Gray400,
    IconDefault = Black,

    BorderDefault = Gray300,
    LineDivider = Gray200,

    GradientStart = Yellow500,
    GradientEnd = SoftOrange
)


// 4. CompositionLocal 등록

val LocalDduruColors = staticCompositionLocalOf { defaultDduruColors }
