package com.infinity8.compose_button_framework.node

import android.graphics.Canvas
import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.layout.MeasureResult

abstract class LayoutNode {

    val children = mutableListOf<LayoutNode>()

    var x = 0f
    var y = 0f

    var width = 0f
    var height = 0f

    private var onInvalidate: (() -> Unit)? = null

    fun setInvalidateCallback(
        callback: () -> Unit
    ) {
        onInvalidate = callback

        children.forEach { child ->
            child.setInvalidateCallback(callback)
        }
    }

    protected fun requestRedraw() {
        onInvalidate?.invoke()
    }

    fun addChild(node: LayoutNode) {

        children.add(node)

        onInvalidate?.let { callback ->
            node.setInvalidateCallback(callback)
        }
    }

    /**
     * Updates animations for this node and its children.
     *
     * @return true when at least one animation is still running.
     */
    open fun updateAnimations(
        frameTimeNanos: Long
    ): Boolean {

        var animating = false

        children.forEach { child ->

            if (child.updateAnimations(frameTimeNanos)) {
                animating = true
            }
        }

        return animating
    }

    open fun onTouchDown(
        x: Float,
        y: Float
    ): Boolean {

        children.asReversed().forEach { child ->

            if (!isInside(
                    x = x,
                    y = y,
                    node = child
                )
            ) {
                return@forEach
            }

            if (child.onTouchDown(x, y)) {
                return true
            }
        }

        return false
    }

    /**
     * ACTION_UP is delivered by Renderer to the node
     * that originally consumed ACTION_DOWN.
     */
    open fun onTouchUp(
        x: Float,
        y: Float
    ): Boolean {
        return false
    }

    /**
     * ACTION_CANCEL is delivered by Renderer to the node
     * that originally consumed ACTION_DOWN.
     */
    open fun onTouchCancel(): Boolean {
        return false
    }

    /**
     * Checks whether the supplied point is inside this node.
     */
    fun contains(
        x: Float,
        y: Float
    ): Boolean {

        return isInside(
            x = x,
            y = y,
            node = this
        )
    }

    private fun isInside(
        x: Float,
        y: Float,
        node: LayoutNode
    ): Boolean {

        return x >= node.x &&
                x <= node.x + node.width &&
                y >= node.y &&
                y <= node.y + node.height
    }

    fun removeChildAt(index: Int) {
        children.removeAt(index)
    }

    fun clearChildren() {
        children.clear()
    }

    fun moveChildren(
        from: Int,
        to: Int,
        count: Int
    ) {
        if (count <= 0 || from == to) return

        val moved = children
            .subList(from, from + count)
            .toList()

        children
            .subList(from, from + count)
            .clear()

        var target = to

        if (to > from) {
            target -= count
        }

        children.addAll(
            target,
            moved
        )
    }

    open fun findTouchTarget(
        x: Float,
        y: Float
    ): LayoutNode? {

        children.asReversed().forEach { child ->

            if (!child.contains(x, y)) {
                return@forEach
            }

            val target = child.findTouchTarget(
                x,
                y
            )

            if (target != null) {
                return target
            }
        }

        return null
    }

    abstract fun measure(
        constraints: Constraints
    ): MeasureResult

    abstract fun layout()

    abstract fun draw(
        canvas: Canvas
    )
}