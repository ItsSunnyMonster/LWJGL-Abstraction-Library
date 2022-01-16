package com.sunnymonster.lwjgllib.utils

import org.joml.Vector3f
import org.joml.Vector3i
import org.joml.Vector4f
import org.joml.Vector4i

/**
 * Created on 13/01/2022
 * @author SunnyMonster
 */
class Color {
    private val red : Int
    private val green : Int
    private val blue : Int
    private val alpha : Int

    /**
     * Creates a new color with its hexadecimal value.
     * @param hex The hexadecimal value of the color. It should start with # and it should be 7 or 9 characters long.
     */
    constructor(hex : String) {
        val rgb = hexToRGB(hex)
        red = rgb.x
        green = rgb.y
        blue = rgb.z
        alpha = rgb.w
    }

    /**
     * Creates a new color with its RGB values. Alpha is set to 255 by default.
     * @param red The red value of the color. Should be between 0 and 255.
     * @param green The green value of the color. Should be between 0 and 255.
     * @param blue The blue value of the color.  Should be between 0 and 255.
     * @param alpha The alpha value of the color. Should be between 0 and 255.
     */
    constructor(red : Int, green : Int, blue : Int, alpha : Int = 255) {
        this.red = red
        this.green = green
        this.blue = blue
        this.alpha = alpha
    }

    /**
     * Creates a new color with its normalized RGB values. Alpha is set to 1.0 by default.
     * @param red The red value of the color. Should be between 0.0 and 1.0.
     * @param green The green value of the color. Should be between 0.0 and 1.0.
     * @param blue The blue value of the color.  Should be between 0.0 and 1.0.
     * @param alpha The alpha value of the color. Should be between 0.0 and 1.0.
     */
    constructor(red : Float, green : Float, blue : Float, alpha : Float = 1.0f) {
        this.red = (red * 255).toInt()
        this.green = (green * 255).toInt()
        this.blue = (blue * 255).toInt()
        this.alpha = (alpha * 255).toInt()
    }

    /**
     * Creates a new color with its RGB values.
     * @param rgb The color. The components should be between 0 and 255.
     */
    constructor(rgb : Vector4i) {
        red = rgb.x
        green = rgb.y
        blue = rgb.z
        alpha = rgb.w
    }

    /**
     * Creates a new color with its RGB values. Alpha is set to 255 by default.
     * @param rgb The color. The components should be between 0 and 255.
     */
    constructor(rgb : Vector3i) {
        red = rgb.x
        green = rgb.y
        blue = rgb.z
        alpha = 255
    }

    /**
     * Creates a new color with its normalized RGB values. Alpha is set to 1.0 by default.
     * @param rgb The color. The components should be between 0.0 and 1.0.
     */
    constructor(rgb : Vector3f) {
        red = (rgb.x * 255).toInt()
        green = (rgb.y * 255).toInt()
        blue = (rgb.z * 255).toInt()
        alpha = 255
    }

    /**
     * Creates a new color with its normalized RGB values.
     * @param rgb The color. The components should be between 0.0 and 1.0.
     */
    constructor(rgb : Vector4f) {
        red = (rgb.x * 255).toInt()
        green = (rgb.y * 255).toInt()
        blue = (rgb.z * 255).toInt()
        alpha = (rgb.w * 255).toInt()
    }

    /**
     * Getter for the red component of the color.
     * @return The red component of the color.
     */
    fun r() : Int {
        return red
    }

    /**
     * Getter for the green component of the color.
     * @return The green component of the color.
     */
    fun g() : Int {
        return green
    }

    /**
     * Getter for the blue component of the color.
     * @return The blue component of the color.
     */
    fun b() : Int {
        return blue
    }

    /**
     * Getter for the alpha component of the color.
     * @return The alpha component of the color.
     */
    fun a() : Int {
        return alpha
    }

    /**
     * Normalizes the color to a Vector4f.
     * @return The normalized color.
     */
    fun normalize() : Vector4f {
        return Vector4f(red / 255f, green / 255f, blue / 255f, alpha / 255f)
    }

    companion object {
        /**
         * Converts a hexadecimal string to an RGB color.
         *
         * @param hex The hexadecimal string. It should start with # and be 7 or 9 characters long.
         * @return The RGB color.
         */
        @JvmStatic
        fun hexToRGB(hex : String) : Vector4i {
            if (hex.length != 7 && hex.length != 9) {
                throw IllegalArgumentException("Hex color must be 7 or 9 characters long including the #")
            }
            if (!hex.startsWith("#")) {
                throw IllegalArgumentException("Hex color must start with #")
            }
            val r = hex.substring(1, 3)
            val g = hex.substring(3, 5)
            val b = hex.substring(5, 7)
            var a = "FF"
            if (hex.length == 9) {
                a = hex.substring(7, 9)
            }
            return Vector4i(Integer.parseInt(r, 16), Integer.parseInt(g, 16), Integer.parseInt(b, 16), Integer.parseInt(a, 16))
        }

        @JvmStatic
        val BLACK : Color = Color("#000000")
        @JvmStatic
        val RED : Color = Color("#FF0000")
        @JvmStatic
        val GREEN : Color = Color("#00FF00")
        @JvmStatic
        val BLUE : Color = Color("#0000FF")
        @JvmStatic
        val YELLOW : Color = Color("#FFFF00")
        @JvmStatic
        val CYAN : Color = Color("#00FFFF")
        @JvmStatic
        val MAGENTA : Color = Color("#FF00FF")
        @JvmStatic
        val WHITE : Color = Color("#FFFFFF")
    }
}