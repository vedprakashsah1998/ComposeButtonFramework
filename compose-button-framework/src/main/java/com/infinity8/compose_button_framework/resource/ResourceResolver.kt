package com.infinity8.compose_button_framework.resource

import android.graphics.Typeface
import com.infinity8.compose_button_framework.FontFamily
import com.infinity8.compose_button_framework.FontWeight

interface FontFamilyResolver {
    fun resolve(
        family: FontFamily,
        weight: FontWeight,
        style: FontStyle = FontStyle.Normal
    ): Typeface
}