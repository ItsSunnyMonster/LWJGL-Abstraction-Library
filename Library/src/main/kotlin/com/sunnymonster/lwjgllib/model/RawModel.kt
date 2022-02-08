package com.sunnymonster.lwjgllib.model

import com.sunnymonster.lwjgllib.gl.VertexArray
import com.sunnymonster.lwjgllib.gl.buffer.IndexBuffer
import com.sunnymonster.lwjgllib.gl.buffer.VertexBuffer
import com.sunnymonster.lwjgllib.gl.shader.RawModelShader

/**
 * Created on 30/01/2022
 * @author SunnyMonster
 * @constructor Creates a new RawModel
 * @param positions Vertex positions (Should be 3D)
 * @param triangles Indices (Should be given counterclockwise)
 */
class RawModel(positions : FloatArray, triangles : IntArray) : AutoCloseable {
    private val vertexArray : VertexArray
    private val indexBuffer : IndexBuffer

    init {
        val positionVBO = VertexBuffer(positions, 3)
        indexBuffer = IndexBuffer(triangles)

        vertexArray = VertexArray()
        vertexArray.addBuffer(positionVBO)
        positionVBO.close()
    }

    /**
     * Binds the vertex array, the index buffer tied to the raw model
     */
    fun bind() {
        vertexArray.bind()
        indexBuffer.bind()
    }

    /**
     * Unbinds the vertex array, the index buffer and the shader tied to the raw model
     */
    fun unbind() {
        vertexArray.unbind()
        indexBuffer.unbind()
    }

    /**
     * Getter for the vertex array tied to the raw model
     * @return The vertex array tied to the raw model
     */
    fun vertexArray() : VertexArray {
        return vertexArray
    }

    /**
     * Getter for the index buffer tied to the raw model
     * @return The index buffer tied to the raw model
     */
    fun indexBuffer() : IndexBuffer {
        return indexBuffer
    }

    /**
     * Deletes the vertex array, the index buffer and the shader tied to the raw model
     */
    override fun close() {
        vertexArray.close()
        indexBuffer.close()
    }
}