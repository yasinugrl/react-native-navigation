package com.reactnativenavigation.options.params

import android.content.res.Resources
import com.reactnativenavigation.utils.UiUtils

open class DensityPixel(value: Int) : Param<Int>(UiUtils.dpToPx(Resources.getSystem().displayMetrics, value.toFloat()).toInt())
object NullDensityPixel : DensityPixel(0) {
    override fun hasValue() = false
}