package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.BoolProp
import com.reactnativenavigation.options.params.NoValBool
import org.json.JSONObject

object BoolParser {
    @JvmStatic
    fun parse(json: JSONObject, key: String): BoolProp {
        return if (json.has(key)) BoolProp(json.optBoolean(key)) else NoValBool
    }

    @JvmStatic
    fun parseFirst(json: JSONObject, vararg keys: String): BoolProp = keys.firstOrNull { name: String -> json.has(name) }?.let {
        BoolProp(json.optBoolean(it))
    } ?: NoValBool
}