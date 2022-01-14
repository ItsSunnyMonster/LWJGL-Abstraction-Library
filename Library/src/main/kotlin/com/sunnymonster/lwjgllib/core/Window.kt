package com.sunnymonster.lwjgllib.core

import com.sunnymonster.lwjgllib.render.Renderer
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL43.*
import org.lwjgl.opengl.GLDebugMessageCallback
import org.lwjgl.opengl.GLDebugMessageCallbackI
import org.lwjgl.system.MemoryUtil.NULL

class Window
    (props: WindowProps) {
    private val handle : Long
    private var active : Boolean = true
    private val renderer : Renderer

    @Suppress("NAME_SHADOWING")
    private val debugCallback : GLDebugMessageCallbackI = GLDebugMessageCallbackI { source, type, id, severity, length, message, userParam ->
        run {

            val source: String = when (source) {
                GL_DEBUG_SOURCE_API -> "API"
                GL_DEBUG_SOURCE_WINDOW_SYSTEM -> "WINDOW SYSTEM"
                GL_DEBUG_SOURCE_SHADER_COMPILER -> "SHADER COMPILER"
                GL_DEBUG_SOURCE_THIRD_PARTY -> "THIRD PARTY"
                GL_DEBUG_SOURCE_APPLICATION -> "APPLICATION"
                GL_DEBUG_SOURCE_OTHER -> "OTHER"
                else -> "UNKNOWN"
            }

            val type: String = when (type) {
                GL_DEBUG_TYPE_ERROR -> "ERROR"
                GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR -> "DEPRECATED BEHAVIOR"
                GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR -> "UNDEFINED BEHAVIOR"
                GL_DEBUG_TYPE_PORTABILITY -> "PORTABILITY"
                GL_DEBUG_TYPE_PERFORMANCE -> "PERFORMANCE"
                GL_DEBUG_TYPE_OTHER -> "OTHER"
                GL_DEBUG_TYPE_MARKER -> "MARKER"
                GL_DEBUG_TYPE_PUSH_GROUP -> "PUSH GROUP"
                GL_DEBUG_TYPE_POP_GROUP -> "POP GROUP"
                else -> "UNKNOWN"
            }

            val severity: String = when (severity) {
                GL_DEBUG_SEVERITY_HIGH -> "HIGH"
                GL_DEBUG_SEVERITY_MEDIUM -> "MEDIUM"
                GL_DEBUG_SEVERITY_LOW -> "LOW"
                GL_DEBUG_SEVERITY_NOTIFICATION -> "NOTIFICATION"
                else -> "UNKNOWN"
            }

            println(String.format("%d: %s of %s severity, raised from %s: %s\n",
                id, type, severity, source, GLDebugMessageCallback.getMessage(length, message)))
        }
    }

    init {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit())
            throw IllegalStateException("Failed to initialize GLFW!")
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE)
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

        glEnable(GL_DEPTH_TEST)
        glEnable(GL_DEBUG_OUTPUT)
        glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS)

        glDebugMessageCallback(debugCallback, NULL)

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