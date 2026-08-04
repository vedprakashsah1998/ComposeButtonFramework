package com.infinity8.compose_button_framework.modifier

import androidx.compose.ui.unit.Dp

data class PaddingModifier(
    val start: Dp = Dp.Unspecified,
    val top: Dp = Dp.Unspecified,
    val end: Dp = Dp.Unspecified,
    val bottom: Dp = Dp.Unspecified
) : ModifierElement