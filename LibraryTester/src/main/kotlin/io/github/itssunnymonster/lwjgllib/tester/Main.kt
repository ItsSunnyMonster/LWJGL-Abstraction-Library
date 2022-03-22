package io.github.itssunnymonster.lwjgllib.tester

import io.github.itssunnymonster.lwjgllib.core.Window
import io.github.itssunnymonster.lwjgllib.core.WindowProps
import io.github.itssunnymonster.lwjgllib.gl.shader.TexturedModelShader
import io.github.itssunnymonster.lwjgllib.model.RawModel
import io.github.itssunnymonster.lwjgllib.model.TexturedModel
import io.github.itssunnymonster.lwjgllib.utils.Color
import io.github.itssunnymonster.lwjgllib.utils.Consts
import org.lwjgl.Version

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

    val texCoords : FloatArray = floatArrayOf(
        0f, 1f,
        1f, 1f,
        1f, 0f,
        0f, 0f
    )

    val rawModel = RawModel(positions, indices)
    val texturedModel = TexturedModel("${Consts.TEXTURE_ROOT}panda.jpg", rawModel, texCoords)
    val shader = TexturedModelShader()

    while (Window.windowCount() > 0) {
        renderer.clear()
        renderer.render(texturedModel, shader)
        Window.updateAll()
    }

    texturedModel.close()
    shader.close()
}