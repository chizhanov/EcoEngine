package com.github.chizhanov.ecoengine.rendering

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.withSave
import com.github.chizhanov.ecoengine.component.PositionComponent

/**
 * [Transform2DDecorator] applies a translation/rotation/scale transform to the canvas.
 *
 * This decorator is used internally by the [PositionComponent].
 */
class Transform2DDecorator(
    private val transform: Transform2D = Transform2D()
) : Decorator() {


    override fun apply(draw: (Canvas) -> Unit, canvas: Canvas) {
        canvas.withSave {
            canvas.concat(transform.transformMatrix)
            draw(canvas)
        }
    }
}
