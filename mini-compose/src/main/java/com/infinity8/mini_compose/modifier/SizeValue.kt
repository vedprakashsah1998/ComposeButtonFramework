package com.infinity8.mini_compose.modifier

import androidx.compose.ui.unit.Dp

sealed interface SizeValue {

    data class Fixed(
        val dp: Dp
    ) : SizeValue

    object FillMax : SizeValue

    object WrapContent : SizeValue

    data class FillFraction(
        val fraction: Float
    ) : SizeValue
}