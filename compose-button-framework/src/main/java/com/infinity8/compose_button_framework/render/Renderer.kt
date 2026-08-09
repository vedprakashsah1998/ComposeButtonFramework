package com.infinity8.compose_button_framework.render

import android.graphics.Canvas
import android.view.Choreographer
import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.node.LayoutNode
import com.infinity8.compose_button_framework.node.RootNode

class Renderer {

    private lateinit var rootNode: RootNode

    private val choreographer =
        Choreographer.getInstance()

    /**
     * Node that consumed the current ACTION_DOWN.
     *
     * ACTION_UP and ACTION_CANCEL must always be
     * delivered to this same node.
     */
    private var pressedNode: LayoutNode? = null

    /**
     * Callback provided by the Android View/Compose layer.
     *
     * Renderer calls this whenever a new frame
     * needs to be rendered.
     */
    private var invalidateCallback: (() -> Unit)? = null

    /**
     * Prevents scheduling multiple Choreographer
     * callbacks for the same frame.
     */
    private var frameScheduled = false

    var measuredWidth = 0f
        private set

    var measuredHeight = 0f
        private set

    private val frameCallback =
        Choreographer.FrameCallback { frameTimeNanos ->

            frameScheduled = false

            if (!::rootNode.isInitialized) {
                return@FrameCallback
            }

            val stillAnimating =
                rootNode.updateAnimations(
                    frameTimeNanos
                )

            // Draw the current frame.
            invalidateCallback?.invoke()

            // Continue only if an animation is active.
            if (stillAnimating) {
                scheduleFrame()
            }
        }

    fun setInvalidateCallback(
        callback: () -> Unit
    ) {
        invalidateCallback = callback
    }

    fun setRoot(
        root: RootNode
    ) {
        pressedNode = null

        rootNode = root

        root.setInvalidateCallback {
            scheduleFrame()
        }
    }

    private fun scheduleFrame() {

        if (frameScheduled) {
            return
        }

        if (!::rootNode.isInitialized) {
            return
        }

        frameScheduled = true

        choreographer.postFrameCallback(
            frameCallback
        )
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

        val previousNode = pressedNode
        pressedNode = null

        previousNode?.onTouchCancel()

        val target =
            rootNode.findTouchTarget(
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

    fun dispose() {

        if (frameScheduled) {

            choreographer.removeFrameCallback(
                frameCallback
            )

            frameScheduled = false
        }

        pressedNode = null
        invalidateCallback = null
    }
}