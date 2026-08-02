package com.infinity8.mini_compose.layout

data class Constraints(
    val minWidth: Float = 0f,
    val maxWidth: Float = Float.MAX_VALUE,

    val minHeight: Float = 0f,
    val maxHeight: Float = Float.MAX_VALUE
)
