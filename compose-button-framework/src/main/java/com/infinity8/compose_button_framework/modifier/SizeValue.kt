package com.infinity8.compose_button_framework.modifier

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