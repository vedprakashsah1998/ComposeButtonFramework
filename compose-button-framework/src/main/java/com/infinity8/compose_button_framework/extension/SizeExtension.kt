package com.infinity8.compose_button_framework.extension

import androidx.compose.ui.unit.Dp
import com.infinity8.compose_button_framework.modifier.ModifierChain
import com.infinity8.compose_button_framework.modifier.SizeModifier
import com.infinity8.compose_button_framework.modifier.SizeValue

fun ModifierChain.width(width: Dp) =
    then(
        SizeModifier(
            width = SizeValue.Fixed(width)
        )
    )

fun ModifierChain.height(height: Dp) =
    then(
        SizeModifier(
            height = SizeValue.Fixed(height)
        )
    )

fun ModifierChain.size(size: Dp) =
    then(
        SizeModifier(
            width = SizeValue.Fixed(size),
            height = SizeValue.Fixed(size)
        )
    )

fun ModifierChain.size(
    width: Dp,
    height: Dp
) =
    then(
        SizeModifier(
            width = SizeValue.Fixed(width),
            height = SizeValue.Fixed(height)
        )
    )

fun ModifierChain.fillMaxWidth() =
    then(
        SizeModifier(
            width = SizeValue.FillMax
        )
    )

fun ModifierChain.fillMaxHeight() =
    then(
        SizeModifier(
            height = SizeValue.FillMax
        )
    )

fun ModifierChain.fillMaxSize() =
    then(
        SizeModifier(
            width = SizeValue.FillMax,
            height = SizeValue.FillMax
        )
    )

fun ModifierChain.resolveSize(): SizeModifier {

    var width: SizeValue? = null
    var height: SizeValue? = null

    forEach { element ->

        if (element is SizeModifier) {

            if (element.width != null) {
                width = element.width
            }

            if (element.height != null) {
                height = element.height
            }
        }
    }

    return SizeModifier(
        width = width,
        height = height
    )
}