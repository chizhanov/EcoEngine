package com.github.chizhanov.ecoengine.component

import androidx.compose.ui.graphics.Canvas
import com.github.chizhanov.ecoengine.atoms.Anchor
import com.github.chizhanov.ecoengine.atoms.Offset
import com.github.chizhanov.ecoengine.atoms.Size
import com.github.chizhanov.ecoengine.rendering.Decorator
import com.github.chizhanov.ecoengine.rendering.Transform2D
import com.github.chizhanov.ecoengine.rendering.Transform2DDecorator

open class PositionComponent : Component() {

    val transform2D: Transform2D
    var decorator: Decorator

    var position: Offset = Offset(0f, 0f)
        set(value) {
            field = value
            transform2D.position = value
        }

    var size: Size = Size(100f, 100f)

    var scale: Size = Size(1f, 1f)
        set(value) {
            field = value
            transform2D.scale = value
        }

    var angle: Float = 0f
        set(value) {
            field = value
            transform2D.angle = value
        }

    var anchor: Anchor = Anchor.TOP_LEFT
        set(value) {
            field = value
            //_onModifiedSizeOrAnchor
        }

    init {
        transform2D = Transform2D().apply {
            position = this@PositionComponent.position
            angle = this@PositionComponent.angle
            scale = this@PositionComponent.scale
        }

        decorator = Transform2DDecorator(transform2D)
    }

    override fun renderTree(canvas: Canvas) {
        decorator.applyChain({ super.renderTree(it) }, canvas)
    }
}
