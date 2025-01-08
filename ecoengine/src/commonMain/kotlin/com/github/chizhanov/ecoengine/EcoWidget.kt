package com.github.chizhanov.ecoengine

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.chizhanov.ecoengine.scene.Scene

@Composable
fun EcoWidget(scene: Scene) {
    var redrawTrigger by remember { mutableStateOf(0) }
    val onStateChange: () -> Unit = {
        redrawTrigger++
    }

    DisposableEffect(scene) {
        scene.addStateListener(onStateChange)
        onDispose {
            scene.removeStateListener(onStateChange)
            scene.onRemove()
        }
    }

    BoxWithConstraints(
        Modifier.background(scene.background)
    ) {
        LaunchedEffect(constraints) {
            scene.onSizeChange(constraints)
        }
        LaunchedEffect(scene) {
            scene.load()
        }
        RenderBox(scene, redrawTrigger)
    }
}

@Composable
private fun RenderBox(
    scene: Scene,
    redrawTrigger: Int,
) {
    Canvas(Modifier) {
        redrawTrigger.apply {}
        scene.render(drawContext.canvas)
    }
}
