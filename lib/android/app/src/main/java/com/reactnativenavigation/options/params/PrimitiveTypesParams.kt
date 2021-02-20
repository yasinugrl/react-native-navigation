package com.reactnativenavigation.options.params

open class BoolProp(value: Boolean) : Param<Boolean>(value) {
    val isFalseOrUndefined: Boolean get() = !hasValue() || !this.get()
    val isTrueOrUndefined: Boolean get() = !hasValue() || get()
    val isTrue: Boolean get() = hasValue() && get()
    val isFalse: Boolean get() = hasValue() && !get()
}

open class IntProp(value: Int) : Param<Int>(value)

open class FloatProp(value: Float) : Param<Float>(value)

open class Fraction(value: Double) : Param<Double>(value) {
    constructor(float: Float) : this(float.toDouble())
}

open class TextProp(value: String?) : Param<String>(value) {
    fun length() = if (hasValue()) value!!.length else 0
    override fun toString() = if (hasValue()) value!! else "No Value"
}

object NoValBool : BoolProp(false) {
    override fun hasValue() = false
}

object NoValInt : IntProp(0) {
    override fun hasValue() = false
}

object NoValFloat : FloatProp(0f) {
    override fun hasValue() = false
}

object NoValFraction : Fraction(0.0) {
    override fun hasValue() = false
}

object NullTextProp : TextProp("") {
    override fun hasValue() = false
}

