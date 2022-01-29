package com.sunnymonster.lwjgllib.render

import com.sunnymonster.lwjgllib.core.Window
import com.sunnymonster.lwjgllib.gl.VertexArray
import com.sunnymonster.lwjgllib.gl.buffer.IndexBuffer
import com.sunnymonster.lwjgllib.gl.shader.Shader
import com.sunnymonster.lwjgllib.model.RawModel
import com.sunnymonster.lwjgllib.utils.Color
import org.joml.Vector4f
import org.joml.Vector4i
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.opengl.GL11.*

/**
 * Created on 13/01/2022
 * @author SunnyMonster
 * @constructor Creates a new Renderer
 * @param window The window to render to
 */
class Renderer(window : Window) {
    private val window : Window
    private var clearColor : Color = Color.BLACK
    private var normalizedClearColor : Vector4f = Color.BLACK.normalize()

    init {
        this.window = window
        glfwMakeContextCurrent(window.handle())
        glClearColor(normalizedClearColor.x, normalizedClearColor.y, normalizedClearColor.z, normalizedClearColor.w)
    }

    /**
     * Clears the screen
     */
    fun clear() {
        glfwMakeContextCurrent(window.handle())
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    /**
     * Renders the given VertexArray
     * @param vertexArray The VertexArray to render
     * @param shader The shader to use
     * @param indexBuffer The index buffer to use
     */
    fun render(vertexArray: VertexArray, indexBuffer: IndexBuffer, shader: Shader) {
        glfwMakeContextCurrent(window.handle())
        vertexArray.bind()
        indexBuffer.bind()
        shader.bind()
        glDrawElements(GL_TRIANGLES, indexBuffer.count(), GL_UNSIGNED_INT, 0)
        indexBuffer.unbind()
        vertexArray.unbind()
        shader.unbind()
    }

    /**
     * Renders the given raw model
     * @param rawModel The raw model to render
     */
    fun render(rawModel : RawModel) {
        glfwMakeContextCurrent(window.handle())
        rawModel.bind()
        glDrawElements(GL_TRIANGLES, rawModel.indexBuffer().count(), GL_UNSIGNED_INT, 0)
        rawModel.unbind()
    }

    /**
     * Sets the clear color
     * @param color The color to set
     */
    fun setClearColor(color : Color) {
        clearColor = color
        normalizedClearColor = color.normalize()
        glfwMakeContextCurrent(window.handle())
        glClearColor(normalizedClearColor.x, normalizedClearColor.y, normalizedClearColor.z, normalizedClearColor.w)
    }
}