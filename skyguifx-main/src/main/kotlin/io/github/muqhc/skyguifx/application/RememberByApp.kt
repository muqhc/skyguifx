package io.github.muqhc.skyguifx.application

import io.github.muqhc.skyguifx.util.Prop
import kotlin.reflect.KProperty

class RememberByApp<T>(val application: SkyApplication<*>, val data: T, val namespace: String?): Prop<T>{

    var code: String? = null

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (code == null) {
            code = generatePropCode(thisRef, property)
        }
        val remembered = application.dataContainer[code!!]
        @Suppress("UNCHECKED_CAST")
        return if (remembered == null) data
        else remembered.data as T
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (code == null) {
            code = generatePropCode(thisRef, property)
        }
        val remembered = application.dataContainer[code!!]
        if (remembered == null) {
            application.dataContainer[code!!] = MutableState(value)
            return
        }
        remembered.data = value
    }

    fun generatePropCode(thisRef: Any?, property: KProperty<*>): String {
        val preHead = "{" + (thisRef?.javaClass?.simpleName ?: "null") + "}"
        val postHead = "[" + property::class.java.toGenericString() + "]"
        val tail = "(" + namespace + "." + property.name + ")"
        return "$preHead.$postHead.$tail"
    }

}