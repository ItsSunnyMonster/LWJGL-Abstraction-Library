package com.sunnymonster.lwjgllib.gl.buffer

import org.lwjgl.opengl.GL15.*

/**
 * Created on 14/01/2022
 * @author SunnyMonster
 * @constructor Creates a new VertexBuffer
 * @param data The data to be stored in the buffer
 * @param elementsPerVertex The number of elements per vertex
 */
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

    /**
     * Binds the buffer
     */
    fun bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id)
    }

    /**
     * Unbinds the buffer
     */
    fun unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    /**
     * Getter for the elements per vertex
     * @return The elements per vertex
     */
    fun elementsPerVertex() : Int {
        return elementsPerVertex
    }

    /**
     * Deletes the buffer
     */
    override fun close() {
        glDeleteBuffers(id)
    }
}