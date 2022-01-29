package com.sunnymonster.lwjgllib.tester

import com.sunnymonster.lwjgllib.core.Window
import com.sunnymonster.lwjgllib.core.WindowProps
import com.sunnymonster.lwjgllib.gl.VertexArray
import com.sunnymonster.lwjgllib.gl.buffer.IndexBuffer
import com.sunnymonster.lwjgllib.gl.buffer.VertexBuffer
import com.sunnymonster.lwjgllib.gl.shader.StaticShader
import com.sunnymonster.lwjgllib.model.RawModel
import com.sunnymonster.lwjgllib.utils.Color
import org.joml.Vector4f
import org.lwjgl.Version
import org.lwjgl.glfw.GLFW.glfwGetTime
import kotlin.math.sin

fun main() {
    println("LWJGL Version: ${Version.getVersion()}")

    val window = Window(WindowProps())

    val renderer = window.renderer()

    renderer.setClearColor(Color("#00a2ed")) // This is Windows blue

    val positions : FloatArray = floatArrayOf(
        -0.5f,  0.5f, 0.0f, // Top left     0
         0.5f,  0.5f, 0.0f, // Top right    1
         0.5f, -0.5f, 0.0f, // Bottom right 2
        -0.5f, -0.5f, 0.0f  // Bottom left  3
    )

    val indices : IntArray = intArrayOf(
        3, 2, 1,
        0, 3, 1
    )

    val rawModel = RawModel(positions, indices)

    while (Window.windowCount() > 0) {
        renderer.clear()
        renderer.render(rawModel)
        Window.updateAll()
    }

    rawModel.close()
}