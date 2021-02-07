package com.reactnativenavigation.newoptions

typealias PropertySubscriber<reified T> = (value: T) -> Unit

abstract class Property<T>(val name: String, _value: T) {
    private val subscribers = mutableSetOf<PropertySubscriber<T>>()

    var value = _value
        set(value) {
            val changed = field != value
            field = value
            if (changed)
                notifyAllSubscribers()
        }

    fun subscribe(propertySubscriber: PropertySubscriber<T>, initialCall: Boolean = false) {
        this.subscribers += propertySubscriber
        if (initialCall) {
            propertySubscriber(value)
        }
    }

    fun unsubscribe(propertySubscriber: PropertySubscriber<T>) {
        this.subscribers -= propertySubscriber
    }

    protected fun notifyAllSubscribers() = this.subscribers.forEach { it(this.value) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Property<*>) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


    open fun merge(prop: Property<*>) {
        this.value = prop.value as T
    }

    override fun toString(): String {
        return "Property(name='$name', subscribers=${subscribers.size}, value=$value)"
    }


}

object NullProperty : Property<Unit>("null", Unit)

class IntProperty(name: String, value: Int) : Property<Int>(name, value)
class StringProperty(name: String, value: String) : Property<String>(name, value)
class BooleanProperty(name: String, value: Boolean) : Property<Boolean>(name, value)
class DoubleProperty(name: String, value: Double) : Property<Double>(name, value)
class LongProperty(name: String, value: Long) : Property<Long>(name, value)
open class ListProperty<T>(name: String, value: List<T>) : Property<List<T>>(name, value) {
    operator fun get(index: Int) = this.value[index]
    fun length() = this.value.size
}

class OptionsProperty(name: String, value: Options) : Property<Options>(name, value) {
    val isEmpty: Boolean
        get() = this.value.isEmpty()

    operator fun get(propName: String): Property<*>? = this.value[propName]
    override fun merge(prop: Property<*>) {
        if (prop is OptionsProperty) {
            val mergeResult = this.value.merge(prop.value)
            if (mergeResult.newProps.isNotEmpty()) {
                notifyAllSubscribers()
            }
        }
    }

}
