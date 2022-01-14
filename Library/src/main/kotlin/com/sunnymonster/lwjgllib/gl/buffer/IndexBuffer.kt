package com.sunnymonster.lwjgllib.gl.buffer

import org.lwjgl.opengl.GL15.*

class IndexBuffer(data : IntArray) : AutoCloseable {
    @Suppress("JoinDeclarationAndAssignment")
    private val id : Int

    private val count : Int

    init {
        id = glGenBuffers()
        this.count = data.size
        bind()
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW)
    }

    fun bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
    }

    fun unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    fun count() : Int {
        return count
    }

    override fun close() {
        glDeleteBuffers(id)
    }
}