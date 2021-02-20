package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.FloatProp
import com.reactnativenavigation.options.params.NoValFloat
import org.json.JSONObject

object FloatParser {
    fun parse(json: JSONObject, number: String?): FloatProp {
        return if (json.has(number)) FloatProp(json.optDouble(number).toFloat()) else NoValFloat
    }
}