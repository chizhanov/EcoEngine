package com.github.chizhanov.ecoengine.scene

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

class Looper : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob()

    private val listeners = mutableListOf<(Long) -> Unit>()

    fun addLoop(listener: (Long) -> Unit) {
        listeners.add(listener)
    }

    fun removeLoop(listener: (Long) -> Unit) {
        listeners.remove(listener)
    }

    private fun loop(dt: Long) {
        listeners.forEach { it(dt) }
    }

    private var loopJon: Job? = null

    fun start() {
        if (loopJon?.isActive == true) throw IllegalStateException("Looper is already started")
        loopJon = launch {
            val clock = Clock.System
            var previousTime = clock.now()
            while (isActive) {
                val currentTime = clock.now()
                val dt = currentTime - previousTime
                previousTime = currentTime
                loop(dt.inWholeMicroseconds)
            }
        }
    }

    fun stop() {
        coroutineContext.cancelChildren()
    }
}
