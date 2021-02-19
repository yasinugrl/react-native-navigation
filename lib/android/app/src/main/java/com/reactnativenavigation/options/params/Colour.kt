package com.reactnativenavigation.options.params

import android.graphics.Color
import androidx.annotation.ColorInt

open class Colour(@ColorInt color: Int) : Param<Int>(color) {
    override fun toString(): String = String.format("#%06X", 0xFFFFFF and get()!!)
    fun hasTransparency() = hasValue() && Color.alpha(value!!) < 1
}

object DontApplyColour : Colour(Color.TRANSPARENT) {
    override fun hasValue() = true
    override fun canApplyValue() = false
    override fun toString() = "NoColor"
}

object NullColor : Colour(0) {
    override fun hasValue() = false
    override fun toString() = "Null Color"
}