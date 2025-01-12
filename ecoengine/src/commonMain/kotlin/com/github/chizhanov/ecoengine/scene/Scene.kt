package com.github.chizhanov.ecoengine.scene

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.unit.Constraints
import com.github.chizhanov.ecoengine.atoms.Size
import com.github.chizhanov.ecoengine.camera.Camera
import com.github.chizhanov.ecoengine.component.Component

/**
 *
 */
open class Scene : Component() {

    val camera = Camera()

    /** This should update the state of the scene. */
    override fun update(dt: Long) {
        updateTree(dt)
        camera.update(dt)
    }

    override fun updateTree(dt: Long) {
        for (child in children) {
            child.updateTree(dt)
        }
    }

    /** This should render the scene. */
    override fun render(canvas: Canvas) {
        renderTree(canvas)
    }

    override fun renderTree(canvas: Canvas) {
        if (!isVisible) return
        canvas.withSave {
            camera.viewport.apply(canvas)
            camera.apply(canvas)
            for (child in children) {
                child.renderTree(canvas)
            }
        }
    }

    var constraints: Constraints? = null
        private set

    /**
     * The scene background color.
     * By default, it will be a black color.
     *
     * It cannot be changed at runtime, because the scene widget does not get
     * rebuild when this value changes.
     */
    open val background: Color = Color.Black

    /**
     * This is the resize hook; every time the scene widget is resized, this hook  is called.
     *
     * The default implementation just sets the new size on the size field
     */
    open fun onSizeChange(constraints: Constraints) {
        this.constraints = constraints
        val size = Size(constraints.maxWidth.toFloat(), constraints.maxHeight.toFloat())
        camera.handleResize(size)
    }

    var isLoaded = false
        private set

    suspend fun load() {
        if (isLoaded) throw IllegalStateException("Scene is already loaded")
        isLoaded = true
        onLoad()
        refreshWidget()
        if (!paused) looper.start()
    }

    /**
     * Method to perform late initialization of the [Scene] class.
     *
     * Usually, this method is the main place where you initialize your [Scene]
     * class. This has several advantages over the traditional constructor:
     *   - this method can be `async`;
     *   - it is invoked when the size of the game widget is already known.
     */
    open suspend fun onLoad() {}

    /** Called when the scene is about to be removed from the Compose widget tree */
    open fun onRemove() {}

    private val looper = Looper().apply {
        addLoop(::loop)
    }

    private fun loop(dt: Long) {
        update(dt)
        refreshWidget()
    }

    /** Pauses or resume the engine */
    var paused = false
        set(value) {
            if (field == value) return
            field = value
            if (value) looper.stop() else looper.start()
        }

    //region StateListeners

    private val stateListeners = mutableListOf<() -> Unit>()

    fun addStateListener(callback: () -> Unit) {
        stateListeners.add(callback)
    }

    fun removeStateListener(callback: () -> Unit) {
        stateListeners.remove(callback)
    }

    /**
     * When a Scene is attached to a `EcoWidget`, this method will force that
     * widget to be rebuilt.
     */
    internal fun refreshWidget() {
        stateListeners.forEach { callback -> callback() }
    }
    //endregion
}
