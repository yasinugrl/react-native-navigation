package com.reactnativenavigation.options

import android.util.Log
import org.json.JSONObject

enum class SplitViewDisplayMode {
    auto,
    visible,
    hidden,
    overlay
}

class SplitViewOptions {
    var displayMode: SplitViewDisplayMode? = SplitViewDisplayMode.visible
    var primaryEdge: Boolean? = true
    var minWidth: Int? = null
    var maxWidth: Int? = null

    fun mergeWith(other: SplitViewOptions) {
        displayMode = other.displayMode ?: displayMode
        primaryEdge = other.primaryEdge ?: primaryEdge
        minWidth = other.minWidth ?: minWidth
        maxWidth = other.maxWidth ?: maxWidth
    }

    fun mergeWithDefault(default: SplitViewOptions) {
        displayMode = displayMode ?: default.displayMode
        primaryEdge = primaryEdge ?: default.primaryEdge
        minWidth = minWidth ?: default.minWidth
        maxWidth = maxWidth ?: default.maxWidth
    }

    companion object {
        @JvmStatic
        fun parse(json: JSONObject?): SplitViewOptions {
            val options = SplitViewOptions()
            options.primaryEdge = (json?.optString("primaryEdge") ?: options.primaryEdge) == "leading"
            options.minWidth = json?.optInt("minWidth") ?: options.minWidth
            options.maxWidth = json?.optInt("maxWidth") ?: options.maxWidth
            return options
        }
    }
}