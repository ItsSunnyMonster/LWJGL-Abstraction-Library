package com.sunnymonster.lwjgllib.gl.shader

import com.sunnymonster.lwjgllib.utils.Color
import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*
import java.io.File
import java.net.URL

open class Shader(vertexShaderPath : String, fragmentShaderPath : String) : AutoCloseable {
    private val id : Int

    init {
        val vertexCodeStream = Shader::class.java.getResourceAsStream(vertexShaderPath)
        val fragmentCodeStream = Shader::class.java.getResourceAsStream(fragmentShaderPath)

        val vertexCode : String = vertexCodeStream!!.bufferedReader().use { it.readText() }
        val fragmentCode : String = fragmentCodeStream!!.bufferedReader().use { it.readText() }

        val vertexShader : Int = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexCode)
        glCompileShader(vertexShader)

        val fragmentShader : Int = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, fragmentCode)
        glCompileShader(fragmentShader)

        id = glCreateProgram()
        glAttachShader(id, vertexShader)
        glAttachShader(id, fragmentShader)
        glLinkProgram(id)

        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)
    }

    fun bind() {
        glUseProgram(id)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun setBool(name : String, value : Boolean) {
        bind()
        glUniform1i(glGetUniformLocation(id, name), if (value) 1 else 0)
    }

    fun setInt(name : String, value : Int) {
        bind()
        glUniform1i(glGetUniformLocation(id, name), value)
    }

    fun setFloat(name : String, value : Float) {
        bind()
        glUniform1f(glGetUniformLocation(id, name), value)
    }

    fun setVec4(name : String, value : Vector4f) {
        bind()
        glUniform4f(glGetUniformLocation(id, name), value.x, value.y, value.z, value.w)
    }

    fun setCol(name : String, value : Color) {
        bind()
        val normalized = value.normalize()
        glUniform4f(glGetUniformLocation(id, name), normalized.x, normalized.y, normalized.z, normalized.w)
    }

    override fun close() {
        glDeleteProgram(id)
    }
}