package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.FloatParam
import com.reactnativenavigation.options.params.NullFloatParam
import org.json.JSONObject

object FloatParser {
    fun parse(json: JSONObject, number: String?): FloatParam {
        return if (json.has(number)) FloatParam(json.optDouble(number).toFloat()) else NullFloatParam
    }
}