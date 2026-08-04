package com.infinity8.compose_button_framework.render

import android.graphics.Canvas
import com.infinity8.compose_button_framework.layout.Constraints

import com.infinity8.compose_button_framework.node.RootNode

class Renderer {
    private lateinit var rootNode: RootNode
    var measuredWidth = 0f
        private set

    var measuredHeight = 0f
        private set
    fun setRoot(root: RootNode) {
        rootNode = root
    }
    fun measure(
        width: Float,
        height: Float
    ) {
        if (!::rootNode.isInitialized) return

        val result = rootNode.measure(
            Constraints(
                maxWidth = width,
                maxHeight = height
            )
        )

        measuredWidth = result.width
        measuredHeight = result.height

        rootNode.layout()
    }

    fun draw(canvas: Canvas) {

        if (!::rootNode.isInitialized) return

        rootNode.draw(canvas)
    }
}