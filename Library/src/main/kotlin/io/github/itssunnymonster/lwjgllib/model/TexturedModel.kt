package io.github.itssunnymonster.lwjgllib.model

import io.github.itssunnymonster.lwjgllib.gl.Texture
import io.github.itssunnymonster.lwjgllib.gl.buffer.VertexBuffer

/**
 * Created on 31/01/2022
 * @author SunnyMonster
 * @constructor Creates a texture and a RawModel
 * @param texturePath The path to the texture
 * @param model The model to render
 */
class TexturedModel(texturePath : String, model : RawModel, texCoords : FloatArray) : AutoCloseable {
    private val texture : Texture
    private val model : RawModel

    init {
        texture = Texture(texturePath)
        this.model = model
        val texCoordsBuffer = VertexBuffer(texCoords, 2)
        this.model.vertexArray().addBuffer(texCoordsBuffer)
    }

    /**
     * Binds the raw model and texture tied to the textured model
     */
    fun bind() {
        model.bind()
        texture.bind()
    }

    /**
     * Unbinds the raw model and texture tied to the textured model
     */
    fun unbind() {
        model.unbind()
        texture.unbind()
    }

    /**
     * Getter for the raw model tied to the textured model
     * @return The raw model tied to the textured model
     */
    fun rawModel() : RawModel {
        return model
    }

    /**
     * Deletes the raw model and texture tied to the textured model
     */
    override fun close() {
        model.close()
        texture.close()
    }
}