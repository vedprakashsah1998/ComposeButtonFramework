package com.infinity8.compose_button_framework.node

import android.graphics.Canvas
import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.layout.MeasureResult

class RootNode : LayoutNode() {

    override fun measure(
        constraints: Constraints
    ): MeasureResult {

        var totalHeight = 0f
        var maxWidth = 0f

        children.forEach { child ->

            val result = child.measure(
                constraints
            )

            totalHeight += result.height

            maxWidth = maxOf(
                maxWidth,
                result.width
            )
        }

        width = maxWidth
        height = totalHeight

        return MeasureResult(
            width,
            height
        )
    }

    override fun layout() {
        var currentY = 0f
        children.forEach { child ->
            child.x = 0f
            child.y = currentY
            child.layout()
            currentY += child.height
        }
    }

    override fun draw(
        canvas: Canvas
    ) {

        children.forEach { child ->
            child.draw(canvas)
        }
    }
}