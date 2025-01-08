package com.github.chizhanov.ecoengine.rendering

import androidx.compose.ui.graphics.Matrix
import com.github.chizhanov.ecoengine.atoms.Offset
import com.github.chizhanov.ecoengine.atoms.Size

class Transform2D {
    private var _recalculate = true

    private val _transformMatrix = Matrix()

    var angle = 0f
        set(value) {
            field = value
            markAsModified()
        }

    var position = Offset(0f, 0f)
        set(value) {
            field = value
            markAsModified()
        }
    var scale = Size(1f, 1f)
        set(value) {
            field = value
            markAsModified()
        }
    var offset = Offset(0f, 0f)
        set(value) {
            field = value
            markAsModified()
        }

    fun flipHorizontally() {
        //_scale.x = -_scale.x
    }

    fun flipVertically() {
        //_scale.y = -_scale.y
    }

    val transformMatrix: Matrix
        get() {
            if (_recalculate) {
                _transformMatrix.apply {
                    reset()
                    translate(position.x, position.y, 0f)
                    rotateZ(angle)
                    scale(scale.width, scale.height, 1f)
                    translate(offset.x, offset.y, 0f)
                }
                _recalculate = false
            }
            return _transformMatrix
        }

    private fun markAsModified() {
        _recalculate = true
    }
}
