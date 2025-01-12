package com.github.chizhanov.ecoengine.camera

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Matrix
import com.github.chizhanov.ecoengine.atoms.Offset
import com.github.chizhanov.ecoengine.atoms.Size
import com.github.chizhanov.ecoengine.camera.viewport.DefaultViewport
import com.github.chizhanov.ecoengine.camera.viewport.Viewport

class Camera : Projector {

    var viewport: Viewport = DefaultViewport()
        set(value) {
            field = value
            combinedProjector = Projector.compose(listOf(this, value))
        }

    var combinedProjector = Projector.compose(listOf(this, viewport))
        private set

    var position: Offset = Offset(0f, 0f)

    var zoom = 1f

    /**
     * Use this method to transform the canvas using the current rules provided by this camera object.
     */
    fun apply(canvas: Canvas) {
        val matrix = transformMatrix()
        canvas.concat(matrix)
    }

    private var _canvasSize: Size? = null
    val canvasSize: Size
        get() {
            return _canvasSize ?: throw IllegalStateException("Property `canvasSize` cannot be accessed before the layout stage")
        }

    fun handleResize(size: Size) {
        _canvasSize = size.copy()
        viewport.resize(canvasSize)
    }

    private val matrix = Matrix()

    private fun transformMatrix(): Matrix {
        val translateX = -position.x * zoom
        val translateY = -position.y * zoom
        val v = matrix.values
        if (v[Matrix.ScaleX] == zoom &&
            v[Matrix.ScaleY] == zoom &&
            v[Matrix.TranslateX] == translateX &&
            v[Matrix.TranslateY] == translateY
        ) {
            return matrix
        }
        matrix.reset()
        matrix.translate(translateX, translateY)
        matrix.scale(zoom, zoom, 1f)
        return matrix
    }

    fun update(dt: Long) = Unit

    override fun unprojectVector(screenCoordinates: Offset): Offset {
        return Offset((screenCoordinates.x / zoom) + position.x, (screenCoordinates.y / zoom) + position.y)
    }

    override fun projectVector(worldCoordinates: Offset): Offset {
        return Offset((worldCoordinates.x - position.x) * zoom, (worldCoordinates.y - position.y) * zoom)
    }

    override fun unscaleVector(screenCoordinates: Offset): Offset {
        return Offset(screenCoordinates.x / zoom, screenCoordinates.y / zoom)
    }

    override fun scaleVector(worldCoordinates: Offset): Offset {
        return Offset(worldCoordinates.x * zoom, worldCoordinates.y * zoom)
    }
}
