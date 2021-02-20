package com.reactnativenavigation.options

import android.animation.TimeInterpolator
import android.view.animation.LinearInterpolator
import com.reactnativenavigation.options.params.*
import com.reactnativenavigation.options.params.IntProp
import com.reactnativenavigation.options.parsers.InterpolationParser
import com.reactnativenavigation.options.parsers.NumberParser
import com.reactnativenavigation.options.parsers.TextParser
import org.json.JSONObject

class SharedElementTransitionOptions {
    var fromId: TextProp = NullTextProp
    var toId: TextProp = NullTextProp
    var duration: IntProp = NoValInt
    var startDelay: IntProp = NoValInt
    var interpolator: TimeInterpolator = LinearInterpolator()

    fun getDuration() = duration[0]?.toLong()?:0
    fun getStartDelay() = startDelay[0]?.toLong()?:0

    companion object {
        @JvmStatic
        fun parse(json: JSONObject?): SharedElementTransitionOptions {
            val transition = SharedElementTransitionOptions()
            if (json == null) return transition
            transition.fromId = TextParser.parse(json, "fromId")
            transition.toId = TextParser.parse(json, "toId")
            transition.duration = NumberParser.parse(json, "duration")
            transition.startDelay = NumberParser.parse(json, "startDelay")
            transition.interpolator = InterpolationParser.parse(json)
            return transition
        }
    }
}