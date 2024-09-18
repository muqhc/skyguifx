package io.github.muqhc.skyguifx.util

import kotlin.reflect.KProperty

interface Prop<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

class SimpleProp<T>(var data: T): Prop<T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return data
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        data = value
    }
}

class Observable<T>(val prop: Prop<T>): Prop<T> by prop {
    val onUpdate = SkyEvent1<T,Unit>()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        prop.setValue(thisRef, property, value)
        onUpdate.handle(value)
    }
}

