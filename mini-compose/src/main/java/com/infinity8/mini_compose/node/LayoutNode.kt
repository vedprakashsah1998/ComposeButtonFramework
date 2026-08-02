package com.infinity8.mini_compose.node

import android.graphics.Canvas
import com.infinity8.mini_compose.layout.Constraints
import com.infinity8.mini_compose.layout.MeasureResult

abstract class LayoutNode{
    val children = mutableListOf<LayoutNode>()

    var x = 0f
    var y = 0f

    var width = 0f
    var height = 0f
    fun addChild(node: LayoutNode) {
        children.add(node)
    }
    fun removeChildAt(index: Int) {
        children.removeAt(index)
    }
    fun clearChildren() {
        children.clear()
    }
    fun moveChildren(from: Int, to: Int, count: Int) {
        if (count <= 0 || from == to) return

        val moved = children.subList(from, from + count).toList()
        children.subList(from, from + count).clear()

        var target = to
        if (to > from) {
            target -= count
        }

        children.addAll(target, moved)
    }
    /**
     * Calculates the size of this node.
     */
    abstract fun measure(
        constraints: Constraints
    ): MeasureResult
    /**
     * Calculates the position of this node.
     */
    abstract fun layout()

    /**
     * Draws this node.
     */
    abstract fun draw(canvas: Canvas)
}