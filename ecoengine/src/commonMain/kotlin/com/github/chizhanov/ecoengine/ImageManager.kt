package com.github.chizhanov.ecoengine

import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.MissingResourceException
import org.jetbrains.compose.resources.decodeToImageBitmap

class ImageManager {
    private val assets = mutableMapOf<String, ImageBitmap>()

    @OptIn(ExperimentalResourceApi::class)
    suspend fun load(path: String): ImageBitmap {
        if (assets.containsKey(path)) {
            return assets[path]!!
        }

        val bytes = readResource(path)
        val image = bytes.decodeToImageBitmap()
        assets[path] = image
        return image
    }

    private val readers = mutableListOf<ResourceReader>()

    fun addRes(res: ResourceReader) {
        readers.add(res)
    }

    private suspend fun readResource(path: String): ByteArray {
        readers.forEach { reader ->
            try {
                return reader.read(path)
            } catch (e: MissingResourceException) {
                // ignore
            }
        }
        throw MissingResourceException(path)
    }
}

fun interface ResourceReader {
    suspend fun read(path: String): ByteArray
}
