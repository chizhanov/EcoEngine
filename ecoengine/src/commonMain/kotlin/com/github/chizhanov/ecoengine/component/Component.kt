package com.github.chizhanov.ecoengine.component

import androidx.compose.ui.graphics.Canvas
import kotlin.properties.Delegates

open class Component {
    val children = mutableListOf<Component>()
    var isVisible: Boolean = true
    var parent: Component? = null
        private set

    var zIndex: Int by Delegates.observable(0) { _, _, _ ->
        parent?.sortChildren()
    }

    private fun sortChildren() {
        children.sortBy { it.zIndex }
    }

    fun add(child: Component) {
        child.parent = this
        val insertionPoint = children.binarySearchBy(child.zIndex) { it.zIndex }
        val index = if (insertionPoint < 0) -insertionPoint - 1 else insertionPoint
        children.add(index, child)
    }

    fun addAll(children: List<Component>) {
        children.forEach { it.parent = this }
        this.children.addAll(children)
        sortChildren()
    }

    fun remove(child: Component) {
        child.parent = null
        children.remove(child)
    }

    open fun update(dt: Long) {}

    open fun updateTree(dt: Long) {
        update(dt)
        for (child in children) {
            child.updateTree(dt)
        }
    }

    open fun render(canvas: Canvas) {}

    open fun renderTree(canvas: Canvas) {
        if (!isVisible) return
        render(canvas)
        // Так как список children всегда отсортирован, отрисовка происходит в правильном порядке.
        for (child in children) {
            child.renderTree(canvas)
        }
    }
}
