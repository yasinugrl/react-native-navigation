package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.DensityPixel
import com.reactnativenavigation.options.params.NullDensityPixel
import org.json.JSONObject

object DensityPixelParser {
    @JvmStatic
    fun parse(json: JSONObject, number: String?): DensityPixel {
        return if (json.has(number)) DensityPixel(json.optInt(number)) else NullDensityPixel
    }
}