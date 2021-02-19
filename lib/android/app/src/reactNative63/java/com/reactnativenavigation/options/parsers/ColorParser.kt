package com.reactnativenavigation.options.parsers

import android.content.Context
import com.facebook.react.bridge.ColorPropConverter
import com.reactnativenavigation.options.params.Colour
import com.reactnativenavigation.options.params.DontApplyColour
import com.reactnativenavigation.options.params.NullColor
import org.json.JSONObject

object ColorParser {
    @JvmStatic
    fun parse(context: Context?, json: JSONObject, colorName: String?): Colour {
        if (json.has(colorName)) {
            val color = json.opt(colorName)
            if (color == null) {
                return DontApplyColour
            } else if (color is Int) {
                return Colour(json.optInt(colorName))
            }
            if (color == "NoColor") {
                return DontApplyColour
            }
            val jsonObject = json.optJSONObject(colorName)
            jsonObject?.let {
                val convertedColor: Any = JSONParser.convert(jsonObject)
                val processedColor = ColorPropConverter.getColor(convertedColor, context)
                if (processedColor != null) {
                    return Colour(processedColor)
                }
            }
        }
        return NullColor
    }
}