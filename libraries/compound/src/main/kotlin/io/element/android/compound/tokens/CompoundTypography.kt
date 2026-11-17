/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2023-2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.compound.tokens

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import io.element.android.compound.tokens.generated.TypographyTokens
import io.element.android.compound.theme.MontserratFontFamily

// 32px (Material) vs 34px, it's the closest one
internal val compoundHeadingXlRegular = TypographyTokens.fontHeadingXlRegular

// both are 28px
internal val compoundHeadingLgRegular = TypographyTokens.fontHeadingLgRegular

// These are the default M3 values, but we're setting them manually so an update in M3 doesn't break our designs
internal val defaultHeadlineSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    lineHeight = 32.sp,
    fontSize = 24.sp,
    letterSpacing = 0.em,
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeightStyle = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.None)
)

// 22px (Material) vs 20px, it's the closest one
internal val compoundHeadingMdRegular = TypographyTokens.fontHeadingMdRegular

// 16px both
internal val compoundBodyLgMedium = TypographyTokens.fontBodyLgMedium

// 14px both
internal val compoundBodyMdMedium = TypographyTokens.fontBodyMdMedium

// 16px both
internal val compoundBodyLgRegular = TypographyTokens.fontBodyLgRegular

// 14px both
internal val compoundBodyMdRegular = TypographyTokens.fontBodyMdRegular

// 12px both
internal val compoundBodySmRegular = TypographyTokens.fontBodySmRegular

// 12px both
internal val compoundBodySmMedium = TypographyTokens.fontBodySmMedium

// 11px both
internal val compoundBodyXsMedium = TypographyTokens.fontBodyXsMedium

internal val compoundTypography = Typography(
    // displayLarge = , 57px (Material) size. We have no equivalent
    // displayMedium = , 45px (Material) size. We have no equivalent
    // displaySmall = , 36px (Material) size. We have no equivalent
    headlineLarge = compoundHeadingXlRegular.copy(fontFamily = MontserratFontFamily),
    headlineMedium = compoundHeadingLgRegular.copy(fontFamily = MontserratFontFamily),
    headlineSmall = defaultHeadlineSmall.copy(fontFamily = MontserratFontFamily),

    titleLarge = compoundHeadingMdRegular.copy(fontFamily = MontserratFontFamily),
    titleMedium = compoundBodyLgMedium.copy(fontFamily = MontserratFontFamily),
    titleSmall = compoundBodyMdMedium.copy(fontFamily = MontserratFontFamily),

    bodyLarge = compoundBodyLgRegular.copy(fontFamily = MontserratFontFamily),
    bodyMedium = compoundBodyMdRegular.copy(fontFamily = MontserratFontFamily),
    bodySmall = compoundBodySmRegular.copy(fontFamily = MontserratFontFamily),

    labelLarge = compoundBodyMdMedium.copy(fontFamily = MontserratFontFamily),
    labelMedium = compoundBodySmMedium.copy(fontFamily = MontserratFontFamily),
    labelSmall = compoundBodyXsMedium.copy(fontFamily = MontserratFontFamily),
)
