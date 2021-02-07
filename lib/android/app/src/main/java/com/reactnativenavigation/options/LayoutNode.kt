package com.reactnativenavigation.options

import org.json.JSONObject
import java.util.*

class LayoutNode(val id: String?, val type: Type?, val data: JSONObject, val children: List<LayoutNode?>) {
    enum class Type {
        Component, ExternalComponent, Stack, BottomTabs, SideMenuRoot, SideMenuCenter, SideMenuLeft, SideMenuRight, TopTabs
    }

    internal constructor(id: String?, type: Type?) : this(id, type, JSONObject(), ArrayList<LayoutNode?>()) {}

    val options: JSONObject
        get() = data.optJSONObject("options")

}