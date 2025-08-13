package com.reactnativenavigation.parse.params

import android.graphics.Color
import androidx.annotation.ColorInt
import com.reactnativenavigation.parse.parsers.ColorParser
import org.json.JSONObject

private const val LIGHT_COLOR_KEY = "light"
private const val DARK_COLOR_KEY = "dark"

class NullThemeColour() : ThemeColour(NullColor(), NullColor()) {
    override fun hasValue(): Boolean {
        return false;
    }
}

open class ThemeColour(private var lightColor: Colour, private var darkColor: Colour) {

    constructor(color: Colour) : this(color, color)

    private fun selectedColor() = lightColor

    fun get(@ColorInt defaultColor: Int?): Int? = selectedColor().get(defaultColor)
    fun get(): Int = selectedColor().get()
    open fun hasValue() = selectedColor().hasValue()


    fun hasTransparency() = selectedColor().hasTransparency()
    fun canApplyValue() = selectedColor().canApplyValue()

    companion object {
        @JvmStatic
        fun of(color: Int) = ThemeColour(Colour(color), Colour(color))

        @JvmStatic
        fun of(light: Int, dark: Int) = ThemeColour(Colour(light), Colour(dark))

        @JvmStatic
        fun parse(json: JSONObject?): ThemeColour {
            return json?.let {
                ThemeColour(
                    ColorParser.parse(json, LIGHT_COLOR_KEY),
                    ColorParser.parse(json, DARK_COLOR_KEY)
                )
            } ?: NullThemeColour()
        }

        @JvmStatic
        fun transparent() = of(Color.TRANSPARENT)

    }
}
