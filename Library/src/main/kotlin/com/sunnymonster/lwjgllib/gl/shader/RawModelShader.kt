package com.sunnymonster.lwjgllib.gl.shader

import com.sunnymonster.lwjgllib.utils.Consts

/**
 * Created on 30/01/2022
 * @author SunnyMonster
 */
class RawModelShader :
    Shader(
        "${Consts.SHADER_SOURCE_ROOT}rawmodel/rawmodel.vert",
        "${Consts.SHADER_SOURCE_ROOT}rawmodel/rawmodel.frag")