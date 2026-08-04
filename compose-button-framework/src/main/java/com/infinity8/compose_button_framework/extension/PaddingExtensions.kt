package com.infinity8.compose_button_framework.extension

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.infinity8.compose_button_framework.layout.PaddingValues
import com.infinity8.compose_button_framework.modifier.ModifierChain
import com.infinity8.compose_button_framework.modifier.PaddingModifier

fun ModifierChain.padding(all: Dp): ModifierChain =
    then(
        PaddingModifier(
            start = all,
            top = all,
            end = all,
            bottom = all
        )
    )

fun ModifierChain.padding(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp
): ModifierChain =
    then(
        PaddingModifier(
            start = horizontal,
            end = horizontal,
            top = vertical,
            bottom = vertical
        )
    )

fun ModifierChain.padding(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
): ModifierChain =
    then(
        PaddingModifier(
            start,
            top,
            end,
            bottom
        )
    )

fun ModifierChain.resolvePadding(): PaddingValues {

    var start = 0.dp
    var top = 0.dp
    var end = 0.dp
    var bottom = 0.dp

    forEach {

        if (it is PaddingModifier) {

            if (it.start != Dp.Unspecified)
                start = it.start

            if (it.top != Dp.Unspecified)
                top = it.top

            if (it.end != Dp.Unspecified)
                end = it.end

            if (it.bottom != Dp.Unspecified)
                bottom = it.bottom
        }
    }

    return PaddingValues(
        start,
        top,
        end,
        bottom
    )
}