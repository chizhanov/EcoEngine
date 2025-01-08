package com.github.chizhanov.ecoengine

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.github.chizhanov.ecoengine.atoms.Anchor
import com.github.chizhanov.ecoengine.atoms.Offset
import com.github.chizhanov.ecoengine.atoms.Size

/**
 * A [Sprite] is a region of an [ImageBitmap] that can be rendered in the Canvas.
 *
 * It might represent the entire image or be one of the pieces a spritesheet is
 * composed of. It holds a reference to the source image from which the region
 * is extracted, and the `src` rectangle is the portion inside that image that
 * is to be rendered (not to be confused with the `dest` rect, which is where
 * in the Canvas the sprite is rendered).
 * It also has a [paint] field that can be overwritten to apply a tint to this
 * [Sprite] (default is white, meaning no tint).
 */
class Sprite(
    var image: ImageBitmap,
) {

    var paint: Paint = Paint().apply { color = Color.White }

    companion object {
        suspend fun load(
            path: String,
            imageManager: ImageManager? = null
        ): Sprite {
            val localImageManager = imageManager ?: EcoEngine.imageManager
            val image = localImageManager.load(path)
            return Sprite(image)
        }
    }

    private val imageWidth: Int
        get() = image.width

    private val imageHeight: Int
        get() = image.height

    val originalSize: Size
        get() = Size(imageWidth.toFloat(), imageHeight.toFloat())

    var srcPosition: Offset = Offset(0f, 0f)
    var srcSize: Size = originalSize

    fun render(
        canvas: Canvas,
        position: Offset = Offset(0f, 0f),
        size: Size? = srcSize,
        anchor: Anchor = Anchor.TOP_LEFT,
        overridePaint: Paint? = null
    ) {
        val srcOffset = IntOffset(
            x = srcPosition.x.toInt(),
            y = srcPosition.y.toInt()
        )

        val srcSize = IntSize(
            width = srcSize.width.toInt(),
            height = srcSize.height.toInt()
        )

        val tmpDstSize = size ?: this.srcSize

        val dstPosition = IntOffset(
            x = (position.x - (anchor.x.toFloat() * tmpDstSize.width)).toInt(),
            y = (position.y - (anchor.y.toFloat() * tmpDstSize.height)).toInt()
        )

        val dstSize = IntSize(
            width = tmpDstSize.width.toInt(),
            height = tmpDstSize.height.toInt()
        )

        val renderPaint = overridePaint ?: paint

        canvas.drawImageRect(
            image = image,
            srcOffset = srcOffset,
            srcSize = srcSize,
            dstOffset = dstPosition,
            dstSize = dstSize,
            paint = renderPaint
        )
    }
}
