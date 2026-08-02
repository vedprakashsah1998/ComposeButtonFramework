package com.infinity8.mini_compose.node

import android.graphics.Canvas
import com.infinity8.mini_compose.layout.Constraints
import com.infinity8.mini_compose.layout.MeasureResult

class RootNode : LayoutNode() {


    override fun measure(
        constraints: Constraints
    ): MeasureResult {

        var totalHeight = 0f
        var maxWidth = 0f

        children.forEach { child ->

            val result = child.measure(constraints)

            totalHeight += result.height
            maxWidth = maxOf(maxWidth, result.width)
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

    override fun draw(canvas: Canvas) {
        children.forEach { child ->
            child.draw(canvas)
        }
    }
}