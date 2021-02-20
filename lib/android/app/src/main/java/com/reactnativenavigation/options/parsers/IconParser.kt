package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.NullTextProp
import com.reactnativenavigation.options.params.TextProp
import org.json.JSONException
import org.json.JSONObject

object IconParser {
    @JvmStatic
    fun parse(json: JSONObject?, key: String): TextProp {
        if (json == null || !json.has(key)) return NullTextProp
        try {
            return if (json[key] is String) TextParser.parse(json, key) else TextParser.parse(json.optJSONObject(key), "uri")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return NullTextProp
    }
}