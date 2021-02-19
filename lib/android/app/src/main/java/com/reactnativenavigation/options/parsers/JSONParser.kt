package com.reactnativenavigation.options.parsers

import com.facebook.react.bridge.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JSONParser {
    fun parse(map: ReadableMap?): JSONObject {
        return map?.keySetIterator()?.let {iterator->
            try {
                val result = JSONObject()
                while (iterator.hasNextKey()) {
                    val key = iterator.nextKey()
                    when (map.getType(key)) {
                        ReadableType.String -> result.put(key, map.getString(key))
                        ReadableType.Number -> result.put(key, parseNumber(map, key))
                        ReadableType.Boolean -> result.put(key, map.getBoolean(key))
                        ReadableType.Array -> result.put(key, parse(map.getArray(key)))
                        ReadableType.Map -> result.put(key, parse(map.getMap(key)))
                        else -> {
                        }
                    }
                }
                result
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }

        } ?: JSONObject()

    }

    fun parse(arr: ReadableArray?): JSONArray {
        val result = JSONArray()
        for (i in 0 until (arr?.size() ?: 0)) {
            when (arr?.getType(i)) {
                ReadableType.String -> result.put(arr.getString(i))
                ReadableType.Number -> result.put(parseNumber(arr, i))
                ReadableType.Boolean -> result.put(arr.getBoolean(i))
                ReadableType.Array -> result.put(parse(arr.getArray(i)))
                ReadableType.Map -> result.put(parse(arr.getMap(i)))
                else -> {
                }
            }
        }
        return result
    }

    private fun parseNumber(map: ReadableMap, key: String): Any {
        return try {
            val doubleValue = map.getDouble(key)
            if (doubleValue % 1 == 0.0) {
                map.getInt(key)
            } else doubleValue
        } catch (e: Exception) {
            map.getInt(key)
        }
    }

    private fun parseNumber(arr: ReadableArray, index: Int): Number {
        return try {
            val doubleValue = arr.getDouble(index)
            if (doubleValue % 1 == 0.0) {
                arr.getInt(index)
            } else doubleValue
        } catch (e: Exception) {
            arr.getInt(index)
        }
    }

    fun convert(jsonObject: JSONObject): WritableMap {
        val map: WritableMap = WritableNativeMap()
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            when (val value = jsonObject.opt(key)) {
                is JSONObject -> map.putMap(key, convert(value))
                is JSONArray -> map.putArray(key, convert(value))
                is Boolean -> map.putBoolean(key, value)
                is Int -> map.putInt(key, value)
                is Double -> map.putDouble(key, value)
                is String -> map.putString(key, value)
                else -> map.putString(key, value?.toString())
            }
        }
        return map
    }

    private fun convert(jsonArray: JSONArray): WritableArray {
        val array: WritableArray = WritableNativeArray()
        for (i in 0 until jsonArray.length()) {
            when (val value = jsonArray.opt(i)) {
                is JSONObject -> array.pushMap(convert(value))
                is JSONArray -> array.pushArray(convert(value))
                is Boolean -> array.pushBoolean(value)
                is Int -> array.pushInt(value)
                is Double -> array.pushDouble(value)
                is String -> array.pushString(value)
                else -> array.pushString(value.toString())
            }
        }
        return array
    }
}