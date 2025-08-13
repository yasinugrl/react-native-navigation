package com.reactnativenavigation.options.layout

import com.reactnativenavigation.parse.LayoutDirection
import com.reactnativenavigation.parse.OrientationOptions
import com.reactnativenavigation.parse.params.*
import com.reactnativenavigation.parse.params.ThemeColour
import com.reactnativenavigation.parse.params.Number
import com.reactnativenavigation.parse.parsers.BoolParser
import com.reactnativenavigation.parse.parsers.NumberParser
import org.json.JSONObject

class LayoutOptions {
    @JvmField
    var backgroundColor: ThemeColour = NullThemeColour()

    @JvmField
    var componentBackgroundColor: ThemeColour = NullThemeColour()

    @JvmField
    var topMargin: Number = NullNumber()

    @JvmField
    var adjustResize: Bool = NullBool()

    @JvmField
    var orientation = OrientationOptions()

    @JvmField
    var direction = LayoutDirection.DEFAULT

    var insets: LayoutInsets = LayoutInsets()


    fun mergeWith(other: LayoutOptions) {
        if (other.backgroundColor.hasValue()) backgroundColor = other.backgroundColor
        if (other.componentBackgroundColor.hasValue()) componentBackgroundColor = other.componentBackgroundColor
        if (other.topMargin.hasValue()) topMargin = other.topMargin
        if (other.orientation.hasValue()) orientation = other.orientation
        if (other.direction.hasValue()) direction = other.direction
        if (other.adjustResize.hasValue()) adjustResize = other.adjustResize
        insets.merge(other.insets, null)
    }

    fun mergeWithDefault(defaultOptions: LayoutOptions) {
        if (!backgroundColor.hasValue()) backgroundColor = defaultOptions.backgroundColor
        if (!componentBackgroundColor.hasValue()) componentBackgroundColor = defaultOptions.componentBackgroundColor
        if (!topMargin.hasValue()) topMargin = defaultOptions.topMargin
        if (!orientation.hasValue()) orientation = defaultOptions.orientation
        if (!direction.hasValue()) direction = defaultOptions.direction
        if (!adjustResize.hasValue()) adjustResize = defaultOptions.adjustResize
        insets.merge(null, defaultOptions.insets)

    }

    companion object {
        @JvmStatic
        fun parse(json: JSONObject?): LayoutOptions {
            val result = LayoutOptions()
            if (json == null) return result
            result.backgroundColor = ThemeColour.parse(json.optJSONObject("backgroundColor"))
            result.componentBackgroundColor = ThemeColour.parse(json.optJSONObject("componentBackgroundColor"))
            result.topMargin = NumberParser.parse(json, "topMargin")
            result.insets = LayoutInsets.parse(json.optJSONObject("insets"))
            result.orientation = OrientationOptions.parse(json)
            result.direction = LayoutDirection.fromString(json.optString("direction", ""))
            result.adjustResize = BoolParser.parse(json, "adjustResize")
            return result
        }
    }

}
