package com.sunnymonster.lwjgllib.gl

import com.sunnymonster.lwjgllib.utils.Color
import com.sunnymonster.lwjgllib.utils.Utils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.GL_CLAMP_TO_BORDER
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage.*
import java.nio.ByteBuffer
import javax.imageio.ImageIO

/**
 * Created on 31/01/2022
 * @author SunnyMonster
 * @constructor Creates a new texture and binds it to the current context
 * @param path The path to the texture
 */
class Texture(path : String, isRgba : Boolean) : AutoCloseable {
    private val id : Int
    private val width : Int
    private val height : Int
    private val isRgba : Boolean

    init {
        val imageBuffer = ImageIO.read(this.javaClass.getResourceAsStream(path))

        this.width = imageBuffer.width
        this.height = imageBuffer.height

        id = glGenTextures()
        bind()
        this.isRgba = isRgba

        val pixels = IntArray(width * height)
        imageBuffer.getRGB(0, 0, width, height, pixels, 0, width)
        val byteBuffer = ByteBuffer.allocateDirect(width * height * 4)

        for (h in 0 until height) {
            for (w in 0 until width) {
                val pixel = pixels[h * width + w]
                byteBuffer.put((pixel shr 16 and 0xFF).toByte())
                byteBuffer.put((pixel shr 8 and 0xFF).toByte())
                byteBuffer.put((pixel and 0xFF).toByte())
                byteBuffer.put((pixel shr 24 and 0xFF).toByte())
            }
        }

        byteBuffer.flip()

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER)

        val borderColor = Color.BLACK
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, Utils.vector4fToFloatArray(borderColor.normalize()))

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        glTexImage2D(GL_TEXTURE_2D, 0, if (isRgba) GL_RGBA else GL_RGB, width, height, 0, if (isRgba) GL_RGBA else GL_RGB, GL_UNSIGNED_BYTE, byteBuffer)
        glGenerateMipmap(GL_TEXTURE_2D)
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