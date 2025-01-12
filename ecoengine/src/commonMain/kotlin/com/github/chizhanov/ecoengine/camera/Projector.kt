package com.github.chizhanov.ecoengine.camera

import com.github.chizhanov.ecoengine.atoms.Offset

/**
 * A simple interface to mark a class that can perform projection operations
 * from one 2D Euclidian coordinate space to another.
 *
 * This can be a Viewport, a Camera or anything else that exposes such
 * operations to the user.
 */
interface Projector {
    /**
     * Converts a vector in the screen space to the world space.
     *
     * This considers both the translation and scaling transformations.
     */
    fun unprojectVector(screenCoordinates: Offset): Offset

    /**
     * Converts a vector in the world space to the screen space.
     *
     * This considers both the translation and scaling transformations.
     */
    fun projectVector(worldCoordinates: Offset): Offset

    /**
     * Converts a vector representing a delta in the screen space to the world space.
     *
     * This considers only the scaling transformation, as the translations are cancelled in a delta transformation.
     * A delta can be a displacement (difference between two position
     * vectors), a velocity (displacement over time), etc.
     */
    fun unscaleVector(screenCoordinates: Offset): Offset

    /**
     * Converts a vector representing a delta in the world space to the screen space.
     *
     * This considers only the scaling transformation, as the translations are cancelled in a delta transformation.
     * A delta can be a displacement (difference between two position
     * vectors), a velocity (displacement over time), etc.
     */
    fun scaleVector(worldCoordinates: Offset): Offset

    companion object {
        /**
         * Creates a [ComposedProjector] that will apply the provided projectors in order.
         *
         * Use when dealing with multiple coordinate transformations in succession.
         */
        fun compose(projectors: List<Projector>): Projector {
            return ComposedProjector(projectors)
        }
    }
}

class ComposedProjector(private val components: List<Projector>) : Projector {
    override fun scaleVector(worldCoordinates: Offset): Offset {
        return components.fold(worldCoordinates) { previousValue, element ->
            element.scaleVector(previousValue)
        }
    }

    override fun projectVector(worldCoordinates: Offset): Offset {
        return components.fold(worldCoordinates) { previousValue, element ->
            element.projectVector(previousValue)
        }
    }

    override fun unscaleVector(screenCoordinates: Offset): Offset {
        return components.reversed().fold(screenCoordinates) { previousValue, element ->
            element.unscaleVector(previousValue)
        }
    }

    override fun unprojectVector(screenCoordinates: Offset): Offset {
        return components.reversed().fold(screenCoordinates) { previousValue, element ->
            element.unprojectVector(previousValue)
        }
    }
}
