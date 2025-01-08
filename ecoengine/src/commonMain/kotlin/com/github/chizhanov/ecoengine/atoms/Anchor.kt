package com.github.chizhanov.ecoengine.atoms

data class Anchor(val x: Double, val y: Double) {
    companion object {
        val TOP_LEFT = Anchor(0.0, 0.0)
        val TOP_CENTER = Anchor(0.5, 0.0)
        val TOP_RIGHT = Anchor(1.0, 0.0)
        val CENTER_LEFT = Anchor(0.0, 0.5)
        val CENTER = Anchor(0.5, 0.5)
        val CENTER_RIGHT = Anchor(1.0, 0.5)
        val BOTTOM_LEFT = Anchor(0.0, 1.0)
        val BOTTOM_CENTER = Anchor(0.5, 1.0)
        val BOTTOM_RIGHT = Anchor(1.0, 1.0)
    }
}
