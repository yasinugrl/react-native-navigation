package com.reactnativenavigation.options.params
abstract class Param<T> internal constructor(protected var value: T?) {
    private var consumed = false

    fun consume() {
        consumed = true
    }

    fun get(): T {
        if (hasValue()) {
            return value!!
        }
        throw RuntimeException("Tried to get null value!")
    }


    fun nullableGet(defaultValue: T?): T? = if (hasValue()) value else defaultValue

    operator fun get(defaultValue: T): T = if (hasValue()) value!! else defaultValue

    open fun hasValue(): Boolean {
        return value != null && !consumed
    }

    open fun canApplyValue(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Param<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

}