package com.github.chizhanov.ecoengine.camera.viewport

import androidx.compose.ui.graphics.Canvas
import com.github.chizhanov.ecoengine.atoms.Size
import com.github.chizhanov.ecoengine.camera.Projector

abstract class Viewport : Projector {

    /**
     * This configures the viewport with a new raw canvas size.
     * It should immediately affect [effectiveSize] and [canvasSize].
     * This must be called by the engine at startup and also whenever the size changes.
     */
    abstract fun resize(newCanvasSize: Size)

    /** Applies to the Canvas all necessary transformations to apply this viewport. */
    abstract fun apply(canvas: Canvas)

    /**
     * This returns the effective size, after viewport transformation.
     * This is not the game widget size but for all intents and purposes,
     * inside your game, this size should be used as the real one.
     */
    abstract val effectiveSize: Size

    /**
     * This returns the real widget size. This is the raw canvas size as it would be without any viewport.
     *
     * You probably don't need to care about this if you are using a viewport.
     */
    var canvasSize: Size? = null
}
