package io.github.itssunnymonster.lwjgllib.utils

import org.joml.Vector4f

class Utils {
    companion object {
        @JvmStatic
        fun vector4fToFloatArray(vector : Vector4f) : FloatArray {
            return floatArrayOf(vector.x, vector.y, vector.z, vector.w)
        }
    }
}