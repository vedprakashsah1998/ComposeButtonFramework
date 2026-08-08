package com.infinity8.compose_button_framework.render

import android.graphics.Canvas
import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.node.LayoutNode
import com.infinity8.compose_button_framework.node.RootNode

class Renderer {

    private lateinit var rootNode: RootNode

    /**
     * Node that consumed the current ACTION_DOWN.
     *
     * ACTION_UP and ACTION_CANCEL must always be
     * delivered to this same node.
     */
    private var pressedNode: LayoutNode? = null

    var measuredWidth = 0f
        private set

    var measuredHeight = 0f
        private set

    fun setRoot(
        root: RootNode
    ) {
        pressedNode = null
        rootNode = root
    }

    fun measure(
        width: Float,
        height: Float
    ) {

        if (!::rootNode.isInitialized) {
            return
        }

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

    /**
     * ACTION_DOWN
     */
    fun onTouchDown(
        x: Float,
        y: Float
    ): Boolean {

        if (!::rootNode.isInitialized) {
            return false
        }

        // Cancel any stale gesture.
        val previousNode = pressedNode
        pressedNode = null

        previousNode?.onTouchCancel()

        // Find the actual interactive node.
        val target = rootNode.findTouchTarget(
            x = x,
            y = y
        ) ?: return false

        pressedNode = target

        return target.onTouchDown(
            x = x,
            y = y
        )
    }

    /**
     * ACTION_UP
     */
    fun onTouchUp(
        x: Float,
        y: Float
    ): Boolean {

        val node = pressedNode
            ?: return false

        pressedNode = null

        return if (node.contains(x, y)) {

            node.onTouchUp(
                x,
                y
            )

        } else {

            node.onTouchCancel()
        }
    }

    /**
     * ACTION_CANCEL
     */
    fun onTouchCancel(): Boolean {

        val node = pressedNode
            ?: return false

        pressedNode = null

        return node.onTouchCancel()
    }

    fun draw(
        canvas: Canvas
    ) {

        if (!::rootNode.isInitialized) {
            return
        }

        rootNode.draw(canvas)
    }
}