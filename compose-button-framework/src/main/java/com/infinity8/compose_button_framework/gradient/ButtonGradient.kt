package com.infinity8.compose_button_framework.gradient

sealed class ButtonGradient {

    data class Horizontal(
        val colors: List<Int>,
        val startX: Float = 0f,
        val endX: Float = 1f
    ) : ButtonGradient()

    data class Vertical(
        val colors: List<Int>,
        val startY: Float = 0f,
        val endY: Float = 1f
    ) : ButtonGradient()

    data class Diagonal(
        val colors: List<Int>,
        val startX: Float = 0f,
        val startY: Float = 0f,
        val endX: Float = 1f,
        val endY: Float = 1f
    ) : ButtonGradient()

    data class Radial(
        val colors: List<Int>,
        val centerX: Float = 0.5f,
        val centerY: Float = 0.5f,
        val radius: Float = 0.5f
    ) : ButtonGradient()

    data class Sweep(
        val colors: List<Int>,
        val centerX: Float = 0.5f,
        val centerY: Float = 0.5f
    ) : ButtonGradient()
}