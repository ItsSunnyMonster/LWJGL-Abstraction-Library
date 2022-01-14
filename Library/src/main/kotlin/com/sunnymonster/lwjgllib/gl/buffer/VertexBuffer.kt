package com.sunnymonster.lwjgllib.gl.buffer

import org.lwjgl.opengl.GL15.*

class VertexBuffer(data : FloatArray, elementsPerVertex : Int) : AutoCloseable {
    @Suppress("JoinDeclarationAndAssignment")
    private val id : Int
    private val elementsPerVertex : Int

    init {
        id = glGenBuffers()
        this.elementsPerVertex = elementsPerVertex
        bind()
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW)
    }

    fun bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id)
    }

    fun unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun elementsPerVertex() : Int {
        return elementsPerVertex
    }

    override fun close() {
        glDeleteBuffers(id)
    }
}