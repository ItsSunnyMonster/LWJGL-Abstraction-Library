package io.github.itssunnymonster.lwjgllib.gl

import io.github.itssunnymonster.lwjgllib.utils.Color
import io.github.itssunnymonster.lwjgllib.utils.Utils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.GL_CLAMP_TO_BORDER
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryUtil.NULL
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.io.path.writeBytes

/**
 * Created on 31/01/2022
 * @author SunnyMonster
 * @constructor Creates a new texture and binds it to the current context
 * @param path The path to the texture
 */
@Suppress("JoinDeclarationAndAssignment")
class Texture(path : String) : AutoCloseable {
    private val id : Int
    private val width : Int
    private val height : Int
    private val format : Int?

    init {

        id = glGenTextures()

        val width = IntArray(1)
        val height = IntArray(1)
        val channelsInFile = IntArray(1)

        val textureStream = javaClass.getResourceAsStream(path)
        val temp = Files.createTempFile("texture_cache", null)
        val bytes = textureStream!!.readAllBytes()
        temp.writeBytes(bytes)

        stbi_set_flip_vertically_on_load(true)

        val data = stbi_load(temp.pathString, width, height, channelsInFile, 0)
            ?: throw IllegalStateException("Unable to load texture at path $path. I actually can't believe you messed that up. You are a pathetic coder. ")

        format =  when (channelsInFile[0]) {
            1 -> GL_RED
            3 -> GL_RGB
            4 -> GL_RGBA
            else -> null
        }
        if (format == null) {
            throw IllegalStateException("STB Image returned something that's not 1, 3 or 4. I honestly don't know what you've managed to do.")
        }

        this.width = width[0]
        this.height = height[0]

        bind()

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        glTexImage2D(GL_TEXTURE_2D, 0, format, this.width, this.height, 0, format, GL_UNSIGNED_BYTE, data)
        glGenerateMipmap(GL_TEXTURE_2D)

        stbi_image_free(data)

        Files.delete(temp)
    }

    /**
     * Binds the texture
     */
    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    /**
     * Unbinds the texture
     */
    fun unbind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    /**
     * Deletes the texture
     */
    override fun close() {
        glDeleteTextures(id)
    }
}