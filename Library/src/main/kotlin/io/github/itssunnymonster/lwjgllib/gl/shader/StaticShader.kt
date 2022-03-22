package io.github.itssunnymonster.lwjgllib.gl.shader

import io.github.itssunnymonster.lwjgllib.utils.Consts

/**
 * Created on 14/01/2022
 * @author SunnyMonster
 */
class StaticShader :
    Shader(
        "${Consts.SHADER_SOURCE_ROOT}static/static.vert",
        "${Consts.SHADER_SOURCE_ROOT}static/static.frag")