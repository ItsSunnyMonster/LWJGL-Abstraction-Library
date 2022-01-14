package com.sunnymonster.lwjgllib.tester

import com.sunnymonster.lwjgllib.core.Window
import com.sunnymonster.lwjgllib.core.WindowProps
import com.sunnymonster.lwjgllib.gl.VertexArray
import com.sunnymonster.lwjgllib.gl.buffer.IndexBuffer
import com.sunnymonster.lwjgllib.gl.buffer.VertexBuffer
import com.sunnymonster.lwjgllib.gl.shader.StaticShader
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
        0.0f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f
    )

    val indices : IntArray = intArrayOf(
        0, 1, 2
    )

    val positionVbo = VertexBuffer(positions, 3)
    val ibo = IndexBuffer(indices)
    val vao = VertexArray()
    vao.addBuffer(positionVbo)
    positionVbo.close()

    val shader = StaticShader()

    var red : Float = 0.0f
    while (Window.windowCount() > 0) {
        red = sin(glfwGetTime().toFloat()) / 2f + 0.5f
        shader.setVec4("u_color", Vector4f(red, 0.0f, 0.0f, 1.0f))
        renderer.clear()
        renderer.render(vao, ibo, shader)
        Window.updateAll()
    }

    vao.close()
    ibo.close()
    shader.close()
}