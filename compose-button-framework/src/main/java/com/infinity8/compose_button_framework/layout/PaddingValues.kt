package com.infinity8.compose_button_framework.layout

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Represents padding values for each edge.
 */
interface PaddingValues {

    val start: Dp

    val top: Dp

    val end: Dp

    val bottom: Dp

    companion object {
        val Zero: PaddingValues = PaddingValues(0.dp)
    }
}

/**
 * Internal implementation.
 */
private data class PaddingValuesImpl(
    override val start: Dp,
    override val top: Dp,
    override val end: Dp,
    override val bottom: Dp
) : PaddingValues {

    init {
        require(start.value >= 0f) { "Padding must be non-negative." }
        require(top.value >= 0f) { "Padding must be non-negative." }
        require(end.value >= 0f) { "Padding must be non-negative." }
        require(bottom.value >= 0f) { "Padding must be non-negative." }
    }
}

/**
 * Same padding on all sides.
 */
fun PaddingValues(
    all: Dp
): PaddingValues =
    PaddingValuesImpl(
        start = all,
        top = all,
        end = all,
        bottom = all
    )

/**
 * Horizontal and vertical padding.
 */
fun PaddingValues(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp
): PaddingValues =
    PaddingValuesImpl(
        start = horizontal,
        top = vertical,
        end = horizontal,
        bottom = vertical
    )

/**
 * Individual edge padding.
 */
fun PaddingValues(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
): PaddingValues =
    PaddingValuesImpl(
        start = start,
        top = top,
        end = end,
        bottom = bottom
    )