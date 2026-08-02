package com.infinity8.mini_compose.runtime
import com.infinity8.mini_compose.node.LayoutNode
import com.infinity8.mini_compose.node.RootNode
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