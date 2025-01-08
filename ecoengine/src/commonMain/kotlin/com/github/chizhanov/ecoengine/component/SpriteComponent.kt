package com.github.chizhanov.ecoengine.component

import androidx.compose.ui.graphics.Canvas
import com.github.chizhanov.ecoengine.Sprite

class SpriteComponent(
    var sprite: Sprite
) : PositionComponent() {

    override fun render(canvas: Canvas) {
        sprite.render(
            canvas,
            size = size,
            anchor = anchor
        )
    }
}
