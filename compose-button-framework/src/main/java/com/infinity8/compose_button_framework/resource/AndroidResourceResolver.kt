package com.infinity8.compose_button_framework.resource

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.infinity8.compose_button_framework.FontFamily
import com.infinity8.compose_button_framework.FontWeight

class AndroidFontFamilyResolver(
    private val context: Context
) : FontFamilyResolver {

    override fun resolve(
        family: FontFamily,
        weight: FontWeight,
        style: FontStyle
    ): Typeface {

        val baseTypeface = when (family) {

            FontFamily.Default ->
                Typeface.DEFAULT

            FontFamily.SansSerif ->
                Typeface.SANS_SERIF

            FontFamily.Serif ->
                Typeface.SERIF

            FontFamily.Monospace ->
                Typeface.MONOSPACE

            is FontFamily.Loaded ->
                family.typeface

            is FontFamily.Resource ->
                ResourcesCompat.getFont(
                    context,
                    family.resId
                ) ?: Typeface.DEFAULT

            is FontFamily.Resources -> {

                // 1. Exact match (weight + style)
                val matchedFont = family.fonts.firstOrNull {
                    it.weight == weight &&
                            it.style == style
                }
                // 2. Match by weight
                    ?: family.fonts.firstOrNull {
                        it.weight == weight
                    }
                    // 3. Match by style
                    ?: family.fonts.firstOrNull {
                        it.style == style
                    }
                    // 4. Fallback
                    ?: family.fonts.first()

                ResourcesCompat.getFont(
                    context,
                    matchedFont.resId
                ) ?: Typeface.DEFAULT
            }
        }
        if (family is FontFamily.Resource ||
            family is FontFamily.Resources ||
            family is FontFamily.Loaded
        ) {
            return baseTypeface
        }

        val typefaceStyle = when (style) {

            FontStyle.Normal -> {
                when (weight) {
                    FontWeight.Thin,
                    FontWeight.ExtraLight,
                    FontWeight.Light,
                    FontWeight.Normal,
                    FontWeight.Medium ->
                        Typeface.NORMAL

                    FontWeight.SemiBold,
                    FontWeight.Bold,
                    FontWeight.ExtraBold,
                    FontWeight.Black ->
                        Typeface.BOLD
                }
            }

            FontStyle.Italic -> {
                when (weight) {
                    FontWeight.Thin,
                    FontWeight.ExtraLight,
                    FontWeight.Light,
                    FontWeight.Normal,
                    FontWeight.Medium ->
                        Typeface.ITALIC

                    FontWeight.SemiBold,
                    FontWeight.Bold,
                    FontWeight.ExtraBold,
                    FontWeight.Black ->
                        Typeface.BOLD_ITALIC
                }
            }

        }
        return Typeface.create(baseTypeface, typefaceStyle)

    }
}
enum class FontStyle {
    Normal,
    Italic
}