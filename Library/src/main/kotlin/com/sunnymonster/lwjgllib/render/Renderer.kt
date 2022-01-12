package com.sunnymonster.lwjgllib.render

import com.sunnymonster.lwjgllib.core.Window
import com.sunnymonster.lwjgllib.utils.Color
import org.joml.Vector4f
import org.joml.Vector4i
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.opengl.GL11.*

class Renderer(window : Window) {
    private val window : Window
    private var clearColor : Color = Color.BLACK
    private var normalizedClearColor : Vector4f = Color.BLACK.normalize()

    init {
        this.window = window
        glfwMakeContextCurrent(window.handle())
        glClearColor(normalizedClearColor.x, normalizedClearColor.y, normalizedClearColor.z, normalizedClearColor.w)
    }

    fun clear() {
        glfwMakeContextCurrent(window.handle())
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    fun setClearColor(color : Color) {
        clearColor = color
        normalizedClearColor = color.normalize()
        glfwMakeContextCurrent(window.handle())
        glClearColor(normalizedClearColor.x, normalizedClearColor.y, normalizedClearColor.z, normalizedClearColor.w)
    }
}