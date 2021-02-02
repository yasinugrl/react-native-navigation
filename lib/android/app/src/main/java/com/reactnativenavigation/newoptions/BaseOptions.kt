package com.reactnativenavigation.newoptions

import org.json.JSONObject


open class Options {
    protected val properties = mutableListOf<Property<*>>(IntProperty("p1", 1), IntProperty("p2", 2)).map { it.name to it }.toMap().toMutableMap()

    fun addProperty(property: Property<*>) {
        this.properties[property.name] = property
    }

    fun getOrDefault(key: String, default: Property<*> = NullProperty) = this.properties[key] ?: default

    operator fun get(key: String) = this.properties[key]

    fun merge(propertiesToMerge: Map<String, Property<*>>): MergeResult {
        val newPropsToAdd = propertiesToMerge - properties.keys
        val updatedProps = propertiesToMerge - newPropsToAdd.keys
        updatedProps.values.forEach { prop ->
            properties[prop.name] = prop
        }
        properties.putAll(newPropsToAdd)
        return MergeResult(updatedProps.values, newPropsToAdd.values)
    }

    open fun fillJson(jsonObject: JSONObject) {

    }

}

class MergeResult(val updatedProps: Collection<Property<*>>, val newProps: Collection<Property<*>>)
