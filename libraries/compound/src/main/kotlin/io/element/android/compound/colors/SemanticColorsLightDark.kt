/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.compound.colors

import io.element.android.compound.tokens.generated.SemanticColors
import io.element.android.compound.tokens.generated.compoundColorsDark
import io.element.android.compound.tokens.generated.compoundColorsLight
import androidx.compose.ui.graphics.Color

data class SemanticColorsLightDark(
    val light: SemanticColors,
    val dark: SemanticColors,
) {
    companion object {
        private val charliePrimary = Color(0xFF971B2F)
        private val charlieHovered = Color(0xFF801628)
        private val charliePressed = Color(0xFF681020)

        val default = SemanticColorsLightDark(
            light = compoundColorsLight.copy(
                bgActionPrimaryRest = charliePrimary,
                bgActionPrimaryHovered = charlieHovered,
                bgActionPrimaryPressed = charliePressed,
                bgAccentRest = charliePrimary,
                bgAccentHovered = charlieHovered,
                bgAccentPressed = charliePressed,
                bgAccentSelected = charliePrimary,
                textActionAccent = charliePrimary,
                iconAccentPrimary = charliePrimary,
                borderAccentPrimary = charliePrimary,
                textOnSolidPrimary = Color.White,
                iconOnSolidPrimary = Color.White,
            ),
            dark = compoundColorsDark.copy(
                bgActionPrimaryRest = charliePrimary,
                bgActionPrimaryHovered = charlieHovered,
                bgActionPrimaryPressed = charliePressed,
                bgAccentRest = charliePrimary,
                bgAccentHovered = charlieHovered,
                bgAccentPressed = charliePressed,
                bgAccentSelected = charliePrimary,
                textActionAccent = charliePrimary,
                iconAccentPrimary = charliePrimary,
                borderAccentPrimary = charliePrimary,
                textOnSolidPrimary = Color.White,
                iconOnSolidPrimary = Color.White,
            ),
        )
    }
}
