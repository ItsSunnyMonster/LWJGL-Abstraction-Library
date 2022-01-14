package com.sunnymonster.lwjgllib.gl

import com.sunnymonster.lwjgllib.gl.buffer.VertexBuffer
import org.lwjgl.opengl.GL30.*

class VertexArray : AutoCloseable {
    @Suppress("JoinDeclarationAndAssignment")
    private val id : Int

    private var index : Int = 0

    init {
        id = glGenVertexArrays()
    }

    fun bind() {
        glBindVertexArray(id)
    }

    fun unbind() {
        glBindVertexArray(0)
    }

    fun addBuffer(buffer : VertexBuffer) {
        bind()
        buffer.bind()
        glVertexAttribPointer(index, buffer.elementsPerVertex(), GL_FLOAT, false, 3 * Float.SIZE_BYTES, 0)
        glEnableVertexAttribArray(index)
        index++
        unbind()
        buffer.unbind()
    }

    override fun close() {
        glDeleteVertexArrays(id)
    }
}