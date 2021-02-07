package com.reactnativenavigation.newoptions

import org.json.JSONArray
import org.json.JSONObject


fun jsonArrayOptionsParser(name: String, jsonArray: JSONArray): ListProperty<Any> {
    val size = jsonArray.length()
    if (size == 0) {
        return ListProperty(name, emptyList())
    }
    val res = mutableListOf<Any>()
    for (i in 0 until size) {
        when (val value = jsonArray.get(i)) {
            is JSONObject -> res += jsonOptionsParser(value)
            else -> res += value
        }
    }
    return ListProperty(name, res)
}

fun jsonOptionsParser(jsonObject: JSONObject): Options {
    val keys = jsonObject.keys()
    val propNames = keys.asSequence().toList()
    return propNames.filter { !jsonObject.isNull(it) }.fold(Options(), { acc: Options, propName: String ->
        acc += when (val value = jsonObject.get(propName)) {
            is String -> StringProperty(propName, value)
            is Int -> IntProperty(propName, value)
            is Boolean -> BooleanProperty(propName, value)
            is Double -> DoubleProperty(propName, value)
            is Long -> LongProperty(propName, value)
            is JSONObject -> OptionsProperty(propName, if (value.length() == 0) Options() else jsonOptionsParser(value))
            is JSONArray -> jsonArrayOptionsParser(propName, value)
            else -> throw RuntimeException("Failed to Parse type of value from json with name:$propName, value:$value")
        }
        acc
    })
}

open class Options {
    private val properties: MutableMap<String, Property<*>> = mutableMapOf()

    fun getOrDefault(key: String, default: Property<*> = NullProperty) = this.properties[key] ?: default

    operator fun get(key: String): Property<*>? = this.properties[key]

    operator fun plusAssign(property: Property<*>) {
        this.properties[property.name] = property
    }

    private fun merge(propertiesToMerge: Map<String, Property<*>>): MergeResult {
        val newPropsToAdd = propertiesToMerge - properties.keys
        val updatedProps = propertiesToMerge - newPropsToAdd.keys
        updatedProps.values.forEach { prop ->
            val property = this.properties[prop.name]
            property?.merge(prop)
        }
        properties.putAll(newPropsToAdd)
        return MergeResult(updatedProps.values, newPropsToAdd.values)
    }

    fun merge(options: Options): MergeResult {
        return this.merge(options.properties)
    }

    fun has(key: String): Boolean = this.properties.containsKey(key)

    fun isEmpty() = properties.isEmpty()

    override fun toString(): String {
        return "Options(properties=$properties)"
    }
}


class MergeResult(val updatedProps: Collection<Property<*>>, val newProps: Collection<Property<*>>)
