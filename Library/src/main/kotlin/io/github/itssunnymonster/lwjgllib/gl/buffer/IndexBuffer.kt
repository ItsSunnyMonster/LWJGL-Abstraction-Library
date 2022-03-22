package io.github.itssunnymonster.lwjgllib.gl.buffer

import org.lwjgl.opengl.GL15.*

/**
 * Created on 14/01/2022
 * @author SunnyMonster
 * @constructor Creates a new index buffer on the GPU
 * @param data The data to be stored in the buffer
 */
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

    /**
     * Binds the buffer
     */
    fun bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
    }

    /**
     * Unbinds the buffer
     */
    fun unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    /**
     * Getter for count
     * @return The count of indices
     */
    fun count() : Int {
        return count
    }

    /**
     * Deletes the buffer
     */
    override fun close() {
        glDeleteBuffers(id)
    }
}