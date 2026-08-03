package com.infinity8.mini_compose.modifier

import androidx.compose.ui.unit.Dp

data class PaddingModifier(
    val start: Dp = Dp.Unspecified,
    val top: Dp = Dp.Unspecified,
    val end: Dp = Dp.Unspecified,
    val bottom: Dp = Dp.Unspecified
) : ModifierElement