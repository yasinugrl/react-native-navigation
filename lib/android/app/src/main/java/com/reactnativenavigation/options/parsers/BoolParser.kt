package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.BoolParam
import com.reactnativenavigation.options.params.NullBoolParam
import org.json.JSONObject

object BoolParser {
    @JvmStatic
    fun parse(json: JSONObject, key: String): BoolParam {
        return if (json.has(key)) BoolParam(json.optBoolean(key)) else NullBoolParam
    }

    @JvmStatic
    fun parseFirst(json: JSONObject, vararg keys: String): BoolParam = keys.firstOrNull { name: String -> json.has(name) }?.let {
        BoolParam(json.optBoolean(it))
    } ?: NullBoolParam
}