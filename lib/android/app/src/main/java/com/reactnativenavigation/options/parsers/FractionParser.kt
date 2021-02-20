package com.reactnativenavigation.options.parsers

import com.reactnativenavigation.options.params.Fraction
import com.reactnativenavigation.options.params.NoValFraction
import org.json.JSONObject

object FractionParser {
    @JvmStatic
    fun parse(json: JSONObject, fraction: String?): Fraction {
        return if (json.has(fraction)) Fraction(json.optDouble(fraction)) else NoValFraction()
    }
}