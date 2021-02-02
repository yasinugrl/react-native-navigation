package com.reactnativenavigation.newoptions

abstract class Property<T>(val name: String, var value: T) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Property<*>) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}

object NullProperty : Property<Unit>("null", Unit)

class IntProperty(name: String, value: Int) : Property<Int>(name, value)
class StringProperty(name: String, value: String) : Property<String>(name, value)
class FloatProperty(name: String, value: Float) : Property<Float>(name, value)
class DoubleProperty(name: String, value: Double) : Property<Double>(name, value)

class OptionsProperty(name: String, value: Options) : Property<Options>(name, value)
