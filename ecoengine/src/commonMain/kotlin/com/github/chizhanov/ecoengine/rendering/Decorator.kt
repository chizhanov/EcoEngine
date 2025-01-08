package com.github.chizhanov.ecoengine.rendering

import androidx.compose.ui.graphics.Canvas

/**
 * [Decorator] is an abstract class that encapsulates a particular visual
 * effect that should apply to drawing commands wrapped by this class.
 *
 * The simplest way to apply a [Decorator] to a component is to override its
 * `renderTree` method like this:
 * ```Kotlin
 * override fun renderTree(canvas: Canvas) {
 *    decorator.applyChain({ super.renderTree(it) }, canvas)
 * }
 * ```
 *
 * Decorators have ability to form a chain, where multiple decorators can be
 * applied in a sequence. This chain is essentially a unary tree, or a linked
 * list: each decorator knows only about the next decorator on the chain.
 */
abstract class Decorator {
    /** The next decorator in the chain, or null if there is none. */
    private var next: Decorator? = null

    /**
     * Applies this and all subsequent decorators if any.
     *
     * This method is the main method through which the decorator is applied.
     */
    fun applyChain(draw: (Canvas) -> Unit, canvas: Canvas) {
        val applyDraw = next?.let {
            { nextCanvas -> it.applyChain(draw, nextCanvas) }
        } ?: draw

        apply(applyDraw, canvas)
    }

    /**
     * Applies visual effect while drawing on the canvas.
     *
     * The default implementation is a no-op; all other non-trivial decorators transform the canvas before drawing,
     * or perform some other adjustments.
     *
     * This method must be implemented by the subclasses,
     * but it is not available to external users: use applyChain instead.
     */
    protected open fun apply(draw: (Canvas) -> Unit, canvas: Canvas) {
        draw(canvas)
    }

    //#region Decorator chain functionality

    val isLastDecorator: Boolean
        get() = next == null

    /** Adds a new decorator onto the chain of decorators */
    fun addLast(decorator: Decorator) {
        next?.let {
            it.addLast(decorator)
            return
        }
        this.next = decorator
    }

    /** Removes the last decorator from the chain of decorators */
    fun removeLast() {
        val next = next ?: return
        if (next.isLastDecorator) {
            this.next = null
        } else {
            next.removeLast()
        }
    }

    fun replaceLast(decorator: Decorator?) {
        val next = next
        when {
            decorator == null -> removeLast()
            next == null || next.isLastDecorator -> this.next = decorator
            else -> next.replaceLast(decorator)
        }
    }

    //#endregion
}
