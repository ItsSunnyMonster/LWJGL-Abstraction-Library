package com.sunnymonster.lwjgllib.gl.shader

import com.sunnymonster.lwjgllib.utils.Consts

/**
 * Created on 14/01/2022
 * @author SunnyMonster
 */
class StaticShader :
    Shader(
        Consts.SHADER_SOURCE_ROOT + "static/static.vert",
        Consts.SHADER_SOURCE_ROOT + "static/static.frag")