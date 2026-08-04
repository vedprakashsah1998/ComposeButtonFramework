package com.infinity8.compose_button_framework.runtime
import com.infinity8.compose_button_framework.node.LayoutNode
import com.infinity8.compose_button_framework.node.RootNode
class Composer {

    private val rootNode = RootNode()
    fun beginComposition() {
        rootNode.children.clear()
    }
    fun emit(node: LayoutNode) {
        rootNode.addChild(node)
    }

    fun build(): RootNode {
        return rootNode
    }
}