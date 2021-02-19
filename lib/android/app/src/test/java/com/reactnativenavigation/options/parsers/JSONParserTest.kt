package com.reactnativenavigation.options.parsers

import com.facebook.react.bridge.JavaOnlyArray
import com.facebook.react.bridge.JavaOnlyMap
import com.reactnativenavigation.BaseTest
import org.assertj.core.api.Java6Assertions
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test

class JSONParserTest : BaseTest() {
    @Test
    fun parsesMap() {
        val input = JavaOnlyMap()
        input.putString("keyString", "stringValue")
        input.putInt("keyInt", 123)
        input.putDouble("keyDouble", 123.456)
        input.putBoolean("keyBoolean", true)
        input.putArray("keyArray", JavaOnlyArray())
        input.putMap("keyMap", JavaOnlyMap())
        input.putNull("bla")
        val result: JSONObject = JSONParser.parse(input)
        Java6Assertions.assertThat(result.keys()).containsOnly(
                "keyString",
                "keyInt",
                "keyDouble",
                "keyBoolean",
                "keyMap",
                "keyArray")
        Java6Assertions.assertThat(result["keyString"]).isEqualTo("stringValue")
        Java6Assertions.assertThat(result["keyInt"]).isEqualTo(123)
        Java6Assertions.assertThat(result["keyDouble"]).isEqualTo(123.456)
        Java6Assertions.assertThat(result["keyBoolean"]).isEqualTo(true)
        Java6Assertions.assertThat(result.getJSONObject("keyMap").keys()).isEmpty()
        Java6Assertions.assertThat(result.getJSONArray("keyArray").length()).isZero()
    }

    @Test
    fun parsesArrays() {
        val input = JavaOnlyArray()
        input.pushString("Hello")
        input.pushInt(123)
        input.pushDouble(123.456)
        input.pushBoolean(true)
        input.pushArray(JavaOnlyArray())
        input.pushMap(JavaOnlyMap())
        input.pushNull()
        val result: JSONArray = JSONParser.parse(input)
        Java6Assertions.assertThat(result.length()).isEqualTo(6)
        Java6Assertions.assertThat(result[0]).isEqualTo("Hello")
        Java6Assertions.assertThat(result[1]).isEqualTo(123)
        Java6Assertions.assertThat(result[2]).isEqualTo(123.456)
        Java6Assertions.assertThat(result[3]).isEqualTo(true)
        Java6Assertions.assertThat(result.getJSONArray(4).length()).isZero()
        Java6Assertions.assertThat(result.getJSONObject(5).keys()).isEmpty()
    }
}