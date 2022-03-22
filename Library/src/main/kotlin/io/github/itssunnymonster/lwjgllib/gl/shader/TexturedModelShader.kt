package io.github.itssunnymonster.lwjgllib.gl.shader

import io.github.itssunnymonster.lwjgllib.utils.Consts

/**
 * Created on 31/01/2022
 * @author SunnyMonster
 */
class TexturedModelShader : Shader("${Consts.SHADER_SOURCE_ROOT}texturedmodel/texturedmodel.vert", "${Consts.SHADER_SOURCE_ROOT}texturedmodel/texturedmodel.frag")