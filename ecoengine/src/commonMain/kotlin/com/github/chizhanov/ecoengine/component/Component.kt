package com.github.chizhanov.ecoengine.component

import androidx.compose.ui.graphics.Canvas

open class Component {
    val children = mutableListOf<Component>()
    var isVisible: Boolean = true

    fun add(child: Component) {
        children.add(child)
    }

    fun addAll(children: List<Component>) {
        this.children.addAll(children)
    }

    fun remove(child: Component) {
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
        for (child in children) {
            child.renderTree(canvas)
        }
    }
}
