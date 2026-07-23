package com.gildongmu.dduru.core.designsystem.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal)
)

@Immutable
data class DduruTypography(
    val Heading: TextStyle,
    val Title: TextStyle,
    val BodyMain: TextStyle,
    val BodySub: TextStyle,
    val Caption: TextStyle,

    val H1: TextStyle,
    val H2: TextStyle,

    val Title1: TextStyle,
    val Title2: TextStyle,
    val Title3: TextStyle,

    val Body1: TextStyle,
    val Body2: TextStyle,
    val Body3: TextStyle,
    val Body4: TextStyle,

    val Caption1: TextStyle,
    val Caption2: TextStyle,

    val Micro: TextStyle,
    val Micro2: TextStyle,
    val Micro3: TextStyle
)

val defaultDduruTypography = DduruTypography(

    Heading = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),

    Title = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 25.2.sp
    ),

    BodyMain = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp // 16 * 1.5
    ),

    BodySub = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal, // Regular
        fontSize = 14.sp,
        lineHeight = 19.6.sp // 14 * 1.4
    ),

    Caption = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),

    H1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),

    H2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 25.2.sp
    ),

    Title1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 21.6.sp
    ),

    Title2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp
    ),

    Title3 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 16.8.sp
    ),

    Body1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.4.sp
    ),

    Body2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.4.sp
    ),

    Body3 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 19.6.sp
    ),

    Body4 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.8.sp
    ),

    Caption1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),

    Caption2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.8.sp
    ),

    Micro = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 12.sp
    ),

    Micro2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 12.sp
    ),

    Micro3 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 12.sp
    )
)

val LocalDduruTypography = staticCompositionLocalOf { defaultDduruTypography }
