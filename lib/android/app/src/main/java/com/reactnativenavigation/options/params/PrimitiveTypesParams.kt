package com.reactnativenavigation.options.params

open class BoolParam(value: Boolean) : Param<Boolean>(value) {
    val isFalseOrUndefined: Boolean get() = !hasValue() || this.get() == false
    val isTrueOrUndefined: Boolean get() = !hasValue() || get() == true
    val isTrue: Boolean get() = hasValue() && get() == true
    val isFalse: Boolean get() = hasValue() && get() == false
}

open class IntParam(value: Int) : Param<Int>(value)

open class FloatParam(value: Float) : Param<Float>(value)

open class Fraction(value: Double) : Param<Double>(value) {
    constructor(float: Float) : this(float.toDouble())
}

open class StringParam(value: String?) : Param<String>(value) {
    fun length() = if (hasValue()) value!!.length else 0
    override fun toString() = if (hasValue()) value!! else "No Value"
}

object NullBoolParam : BoolParam(false) { override fun hasValue() = false }

object NullIntParam : IntParam(0) { override fun hasValue() = false }

object NullFloatParam : FloatParam(0f) { override fun hasValue() = false }

class NullFraction : Fraction(0.0) { override fun hasValue() = false }

object NullStringParam : StringParam("") { override fun hasValue() = false }

