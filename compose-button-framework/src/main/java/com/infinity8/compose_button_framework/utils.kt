package com.infinity8.compose_button_framework

import android.graphics.Typeface
import androidx.annotation.FontRes
import com.infinity8.compose_button_framework.resource.FontStyle

enum class FontWeight {
    Thin,
    ExtraLight,
    Light,
    Normal,
    Medium,
    SemiBold,
    Bold,
    ExtraBold,
    Black
}

enum class TextOverflow {
    Clip,
    Ellipsis
}

enum class TextTransform {
    None,
    Uppercase,
    Lowercase
}

sealed class FontFamily {

    object Default : FontFamily()

    object SansSerif : FontFamily()

    object Serif : FontFamily()

    object Monospace : FontFamily()
    data class Resource(
        @FontRes val resId: Int
    ) : FontFamily()
    data class Resources(
        val fonts: List<Font>
    ) : FontFamily()
    data class Loaded(
        val typeface: Typeface
    ) : FontFamily()
}

data class Font(
    @FontRes
    val resId: Int,

    val weight: FontWeight = FontWeight.Normal,

    val style: FontStyle = FontStyle.Normal
)