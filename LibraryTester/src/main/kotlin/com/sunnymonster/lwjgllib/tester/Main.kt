package com.sunnymonster.lwjgllib.tester

import com.sunnymonster.lwjgllib.core.Window
import com.sunnymonster.lwjgllib.core.WindowProps
import com.sunnymonster.lwjgllib.utils.Color

fun main() {
    val window = Window(WindowProps())

    val renderer = window.renderer()

    renderer.setClearColor(Color("#00a2ed"))

    while (Window.windowCount() > 0) {
        renderer.clear()
        Window.updateAll()
    }
}