package com.sunnymonster.lwjgllib.core

import com.sunnymonster.lwjgllib.render.Renderer
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil.NULL

class Window
    (props: WindowProps) {
    private val handle : Long
    private var active : Boolean = true
    private val renderer : Renderer

    init {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit())
            throw IllegalStateException("Failed to initialize GLFW!")
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        handle = glfwCreateWindow(props.width, props.height, props.title, NULL, NULL)
        if (handle == NULL)
            throw RuntimeException("Unable to create window!")
        val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        glfwSetWindowPos(handle, (vidMode!!.width() - props.width) / 2, (vidMode.height() - props.height) / 2)
        glfwMakeContextCurrent(handle)
        glfwSwapInterval(1)
        glfwShowWindow(handle)
        GL.createCapabilities()

        renderer = Renderer(this)

        windows.add(this)
    }

    fun update(iterator: MutableIterator<Window>) {
        if (!active)
            return
        glfwSwapBuffers(handle)
        if (glfwWindowShouldClose(handle)) {
            glfwDestroyWindow(handle)
            active = false
            iterator.remove()
        }
    }

    fun handle() : Long {
        return handle
    }

    fun renderer() : Renderer {
        return renderer
    }

    companion object {
        @JvmStatic
        private var windows : MutableList<Window> = ArrayList()

        @JvmStatic
        fun pollEvents() {
            glfwPollEvents()
        }

        @JvmStatic
        fun updateAll() {
            val it = windows.iterator()
            while (it.hasNext()) {
                it.next().update(it)
            }
            pollEvents()
        }

        @JvmStatic
        fun windowCount() : Int {
            return windows.size
        }
    }
}