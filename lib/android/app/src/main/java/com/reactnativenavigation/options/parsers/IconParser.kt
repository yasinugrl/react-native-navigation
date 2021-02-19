package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.NullStringParam
import com.reactnativenavigation.options.params.StringParam
import org.json.JSONException
import org.json.JSONObject

object IconParser {
    @JvmStatic
    fun parse(json: JSONObject?, key: String): StringParam {
        if (json == null || !json.has(key)) return NullStringParam
        try {
            return if (json[key] is String) TextParser.parse(json, key) else TextParser.parse(json.optJSONObject(key), "uri")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return NullStringParam
    }
}