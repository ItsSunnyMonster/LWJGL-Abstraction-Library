package io.github.itssunnymonster.lwjgllib.gl

import io.github.itssunnymonster.lwjgllib.gl.buffer.VertexBuffer
import org.lwjgl.opengl.GL30.*

/**
 * Created on 13/01/2022
 * @author SunnyMonster
 * @constructor Creates a new VertexArray
 */
class VertexArray : AutoCloseable {
    @Suppress("JoinDeclarationAndAssignment")
    private val id : Int

    private var index : Int = 0

    init {
        id = glGenVertexArrays()
    }

    /**
     * Binds the VertexArray
     */
    fun bind() {
        glBindVertexArray(id)
    }

    /**
     * Unbinds the VertexArray
     */
    fun unbind() {
        glBindVertexArray(0)
    }

    /**
     * Adds a VertexBuffer to the VertexArray
     * @param buffer The VertexBuffer to add
     */
    fun addBuffer(buffer : VertexBuffer) {
        bind()
        buffer.bind()
        glVertexAttribPointer(index, buffer.elementsPerVertex(), GL_FLOAT, false, buffer.elementsPerVertex() * Float.SIZE_BYTES, 0)
        glEnableVertexAttribArray(index)
        index++
        unbind()
        buffer.unbind()
    }

    /**
     * Deletes the VertexArray
     */
    override fun close() {
        glDeleteVertexArrays(id)
    }
}