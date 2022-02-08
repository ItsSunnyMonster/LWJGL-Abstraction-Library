package com.sunnymonster.lwjgllib.gl.shader

import com.sunnymonster.lwjgllib.utils.Color
import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*
import java.io.File
import java.net.URL

/**
 * Created on 14/01/2022
 * @author SunnyMonster
 * @constructor Creates a new shader program from the given vertex and fragment shader source files.
 * @param vertexShaderPath The path to the vertex shader source file.
 * @param fragmentShaderPath The path to the fragment shader source file.
 */
open class Shader(vertexShaderPath : String, fragmentShaderPath : String) : AutoCloseable {

    private val id : Int

    init {
        val vertexCodeStream = Shader::class.java.getResourceAsStream(vertexShaderPath)
        val fragmentCodeStream = Shader::class.java.getResourceAsStream(fragmentShaderPath)

        val vertexCode : String = vertexCodeStream!!.bufferedReader().use { it.readText() }
        val fragmentCode : String = fragmentCodeStream!!.bufferedReader().use { it.readText() }

        val success : IntArray = IntArray(1)

        val vertexShader : Int = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexCode)
        glCompileShader(vertexShader)
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, success)
        if (success[0] != GL_TRUE)
        {
            System.err.println(glGetShaderInfoLog(vertexShader))
        }

        val fragmentShader : Int = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, fragmentCode)
        glCompileShader(fragmentShader)
        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, success)
        if (success[0] != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(fragmentShader))
        }

        id = glCreateProgram()
        glAttachShader(id, vertexShader)
        glAttachShader(id, fragmentShader)
        glLinkProgram(id)
        glGetProgramiv(id, GL_LINK_STATUS, success)
        if (success[0] != GL_TRUE) {
            System.err.println(glGetProgramInfoLog(id))
        }

        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)
    }

    /**
     * Binds the shader program
     */
    fun bind() {
        glUseProgram(id)
    }

    /**
     * Unbinds the shader program
     */
    fun unbind() {
        glUseProgram(0)
    }

    /**
     * Sets the value of a uniform variable in the shader program.
     * @param name The name of the uniform variable.
     * @param value The value to set.
     */
    fun setBool(name : String, value : Boolean) {
        bind()
        glUniform1i(glGetUniformLocation(id, name), if (value) 1 else 0)
    }

    /**
     * Sets the value of a uniform variable in the shader program.
     * @param name The name of the uniform variable.
     * @param value The value to set.
     */
    fun setInt(name : String, value : Int) {
        bind()
        glUniform1i(glGetUniformLocation(id, name), value)
    }

    /**
     * Sets the value of a uniform variable in the shader program.
     * @param name The name of the uniform variable.
     * @param value The value to set.
     */
    fun setFloat(name : String, value : Float) {
        bind()
        glUniform1f(glGetUniformLocation(id, name), value)
    }

    /**
     * Sets the value of a uniform variable in the shader program.
     * @param name The name of the uniform variable.
     * @param value The value to set.
     */
    fun setVec4(name : String, value : Vector4f) {
        bind()
        glUniform4f(glGetUniformLocation(id, name), value.x, value.y, value.z, value.w)
    }

    /**
     * Sets the value of a uniform variable in the shader program.
     * @param name The name of the uniform variable.
     * @param value The value to set.
     */
    fun setCol(name : String, value : Color) {
        bind()
        val normalized = value.normalize()
        glUniform4f(glGetUniformLocation(id, name), normalized.x, normalized.y, normalized.z, normalized.w)
    }

    /**
     * Deletes the program
     */
    override fun close() {
        glDeleteProgram(id)
    }
}