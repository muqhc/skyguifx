package io.github.muqhc.skyguifx.util


sealed interface VolatileHandler
class VolatileHandler0<R>(private val func0: ()->R): VolatileHandler, () -> R by func0
class VolatileHandler1<T,R>(private val func1: (T)->R): VolatileHandler, (T) -> R by func1

sealed interface SkyEvent
class SkyEvent0<R>: MutableList<()->R> by mutableListOf() {
    fun handle(): List<R> {
        val result = map { it() }
        removeAll { it is VolatileHandler }
        return result
    }
}
class SkyEvent1<T,R>: MutableList<(T)->R> by mutableListOf() {
    fun handle(arg: T): List<R> {
        val result = map { it(arg) }
        removeAll { it is VolatileHandler }
        return result
    }
}

fun <R> volatile0(func0: ()->R) = VolatileHandler0(func0)
fun <T,R> volatile1(func1: (T)->R) = VolatileHandler1(func1)

infix fun <R> SkyEvent0<R>.promise(func0: ()->R) { add(VolatileHandler0(func0)) }
infix fun <T,R> SkyEvent1<T,R>.promise(func1: (T)->R) { add(VolatileHandler1(func1)) }


