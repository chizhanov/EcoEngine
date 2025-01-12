package com.github.chizhanov.ecoengine.camera.viewport

import androidx.compose.ui.graphics.Canvas
import com.github.chizhanov.ecoengine.atoms.Offset
import com.github.chizhanov.ecoengine.atoms.Size

/**
 * This is the default viewport if you want no transformation.
 * The raw canvasSize is just propagated to the effective size and no translation is applied.
 * This basically no-ops the viewport.
 */
class DefaultViewport : Viewport() {

    override fun resize(newCanvasSize: Size) {
        canvasSize = newCanvasSize.copy()
    }

    override fun apply(canvas: Canvas) {}

    override val effectiveSize: Size
        get() = canvasSize!!

    override fun unprojectVector(screenCoordinates: Offset): Offset = screenCoordinates

    override fun projectVector(worldCoordinates: Offset): Offset = worldCoordinates

    override fun unscaleVector(screenCoordinates: Offset): Offset = screenCoordinates

    override fun scaleVector(worldCoordinates: Offset): Offset = worldCoordinates
}
